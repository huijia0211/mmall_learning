package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Admin
 */
public class RedisShardedPool {
    /**
     * jedis连接池
     */
    private static ShardedJedisPool pool;
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
    private static String ip2 = PropertiesUtil.getProperty("redis.ip2");

    private static Integer port = Integer.parseInt(PropertiesUtil.getProperty("redis.port", "6379"));
    private static Integer port2 = Integer.parseInt(PropertiesUtil.getProperty("redis.port2", "6380"));

    private static String password = PropertiesUtil.getProperty("redis.password");
    private static String password2 = PropertiesUtil.getProperty("redis.password2");

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        JedisShardInfo jedisShardInfo1 = new JedisShardInfo(ip, port, 1000 * 2);
        jedisShardInfo1.setPassword(password);
        JedisShardInfo jedisShardInfo2 = new JedisShardInfo(ip2, port2, 1000 * 2);
        jedisShardInfo2.setPassword(password2);
        List<JedisShardInfo> jedisShardInfoList = new ArrayList<>(2);
        jedisShardInfoList.add(jedisShardInfo1);
        jedisShardInfoList.add(jedisShardInfo2);
        pool = new ShardedJedisPool(config, jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    static {
        initPool();
    }

    public static ShardedJedis getJedis() {
        return pool.getResource();
    }

    public static void returnResource(ShardedJedis jedis) {
        pool.returnResource(jedis);
    }

    public static void returnBrokenResource(ShardedJedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        ShardedJedis jedis = pool.getResource();
//        jedis.set("huijiaKey6", "huijiaValue");
        String value = jedis.get("huijiaKey4");
        System.out.println(value);
        returnBrokenResource(jedis);
        pool.destroy();
    }
}
