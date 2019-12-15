package com.mmall.task;

import com.mmall.common.Const;
import com.mmall.common.RedissonManager;
import com.mmall.service.IOrderService;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Admin
 */
@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private RedissonManager redissonManager;

    //    @Scheduled(cron = "0 */1 * * * ?") //每1分钟
    public void closeOrderTaskV1() {
        log.info("关闭订单定时任务启动");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
        iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }

    public void closeOrderTaskV2() {
        log.info("关闭订单定时任务启动");
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "5000"));
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.RedisLock.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
        //缺陷，当运行到此处时服务器突然关闭，redis锁将一直存在，造成死锁
        if (setnxResult != null && setnxResult.intValue() == 1) {
            //如果返回值是1，代表设置成功，获取锁
            closeOrder(Const.RedisLock.CLOSE_ORDER_TASK_LOCK);
        } else {
            log.info("没有获得分布式锁:{}", Const.RedisLock.CLOSE_ORDER_TASK_LOCK);
        }
        log.info("关闭订单定时任务结束");
    }

    @Scheduled(cron = "0 */1 * * * ?") //每1分钟
    public void closeOrderTaskV3() {
        log.info("关闭订单定时任务启动");
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "5000"));
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.RedisLock.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
        if (setnxResult != null && setnxResult.intValue() == 1) {
            //如果返回值是1，代表设置成功，获取锁
            closeOrder(Const.RedisLock.CLOSE_ORDER_TASK_LOCK);
        } else {
            //未获取到锁，继续判断，
            //判断时间戳，看是否可以重置并获取到锁
            String lockValueStr = RedisShardedPoolUtil.get(Const.RedisLock.CLOSE_ORDER_TASK_LOCK);
            //当当前时间戳大于原锁值，代表原锁超时
            if (lockValueStr != null && System.currentTimeMillis() > Long.parseLong(lockValueStr)) {
                //再次获取原锁值并重新设置锁值
                String getSetResult = RedisShardedPoolUtil.getSet(Const.RedisLock.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
                //再次用当前时间戳getSet
                //返回给定的key的旧值， -> 旧值判断，是否可以获取锁
                //当key没有旧值时，即key不存在时，返回nil -> 获取锁
                //这里我们set了一个新的value值，获取旧的值
                //防止并发，当为null时表示另一个进程定时任务已正常结束过，前value和后value不相同等于value被另一个服务器重新修改过
                if (getSetResult == null || StringUtils.equals(lockValueStr, getSetResult)) {
                    //真正获取到锁
                    closeOrder(Const.RedisLock.CLOSE_ORDER_TASK_LOCK);
                } else {
                    log.info("没有获取到分布式锁:{}", Const.RedisLock.CLOSE_ORDER_TASK_LOCK);
                }
            } else {
                log.info("没有获取到分布式锁:{}", Const.RedisLock.CLOSE_ORDER_TASK_LOCK);
            }
        }
        log.info("关闭订单定时任务结束");
    }

    public void closeOrderTaskV4() {
        RLock lock = redissonManager.getRedisson().getLock(Const.RedisLock.CLOSE_ORDER_TASK_LOCK);
        boolean getLock = false;
        try {
            if (getLock = lock.tryLock(0, 5, TimeUnit.SECONDS)) {
                log.info("Redisson获取分布式锁:{}, ThreadName:{}", Const.RedisLock.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
                int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
                iOrderService.closeOrder(hour);
            } else {
                log.info("Redisson没有获取到分布式锁:{}, ThreadName:{}", Const.RedisLock.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            log.error("Redisson分布式锁获取异常", e);
        } finally {
            //获取到锁，释放锁
            if (getLock) {
                lock.unlock();
                log.error("Redisson分布式释放锁");
            }
        }
    }
    private void closeOrder(String lockName) {
        RedisShardedPoolUtil.expire(lockName, 50);
        log.info("获取{},ThreadName:{}", Const.RedisLock.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
        iOrderService.closeOrder(hour);
        RedisShardedPoolUtil.del(lockName);
        log.info("释放{},ThreadName:{}", Const.RedisLock.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        log.info("=============================================================");
    }

}
