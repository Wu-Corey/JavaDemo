package com.example.javademo.m4_juc.distributedLock;


import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 2021/6/24
 */
@Configuration
public class RedissonConfiguration {

    @Bean
    public Redisson redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://120.79.93.88:6379").setDatabase(0);
        return (Redisson) Redisson.create(config);
    }
}
