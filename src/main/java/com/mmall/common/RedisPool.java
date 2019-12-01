package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Admin
 */
public class RedisPool {
    /**
     * jedis连接池
     */
    private static JedisPool pool;
    /**
     * 最大连接数
     */
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20"));
    /**
     * 在pool中最大的idle状态的jedis实例个数
     */
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10"));
    /**
     * 在pool中最小的idle状态的jedis实例个数
     */
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2"));
    /**
     * 在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true，则得到的jedis实例肯定是可以用的
     */
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow", "true"));
    /**
     * 在return一个jedis实例的时候，是否要进行验证操作，如果赋值true，则放回的jedis实例肯定是可以用的
     */
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return", "true"));

    private static String ip = PropertiesUtil.getProperty("redis.ip");

    private static Integer port = Integer.parseInt(PropertiesUtil.getProperty("redis.port", "6379"));

    private static String password = PropertiesUtil.getProperty("redis.password");

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        pool = new JedisPool(config, ip, port, 1000 * 2, password);
    }

    static {
        initPool();
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void returnResource(Jedis jedis) {
        pool.returnResource(jedis);
    }

    public static void returnBrokenResource(Jedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.set("huijiaKey", "huijiaValue");
        returnBrokenResource(jedis);
        pool.destroy();
    }
}
