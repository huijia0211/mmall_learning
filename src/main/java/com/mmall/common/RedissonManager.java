package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

/**
 * @author Admin
 */
@Component
@Slf4j
public class RedissonManager {

    private Config config = new Config();

    private Redisson redisson = null;

    public Redisson getRedisson() {
        return redisson;
    }

    private static String ip = PropertiesUtil.getProperty("redis.ip");
    private static String ip2 = PropertiesUtil.getProperty("redis.ip2");

    private static Integer port = Integer.parseInt(PropertiesUtil.getProperty("redis.port", "6379"));
    private static Integer port2 = Integer.parseInt(PropertiesUtil.getProperty("redis.port2", "6380"));

    private static String password = PropertiesUtil.getProperty("redis.password");
    private static String password2 = PropertiesUtil.getProperty("redis.password2");


    private void init() {
        try {
            config.useSingleServer().setAddress(new StringBuilder(ip).append(":").append(port).toString()).setPassword(password);
            redisson = (Redisson) Redisson.create(config);
            log.info("初始化Redisson结束");
        } catch (Exception e) {
            log.error("redisson init error", e);
        }
    }
}
