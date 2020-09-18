package com.mmall.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@ContextConfiguration(locations = {"classpath:applicationContext-spring-session.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisShardedPoolTest {

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Test
    public void test() {
        ShardedJedis jedis = shardedJedisPool.getResource();
//        jedis.set("huijiaKey8", "huijiaValueweewrweasdff");

        String value = jedis.get("huijiaKey8");
        System.out.println(value);
        shardedJedisPool.returnResource(jedis);
        shardedJedisPool.destroy();
    }
}
