package com.example.javademo.m4_juc.distributedLock;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.TimeUnit;

/**
 * 2021/6/23
 * Redis实现分布式锁
 */
@Controller
@RequestMapping("test")
public class T_1_RedisDistLock {

    private StringRedisTemplate redisTemplate;

    @GetMapping("deduct_lock")
    public String deductLock() {

        String lockKey = "lockKey";
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey, "123",10, TimeUnit.SECONDS);
        if (!lock) {
            return "没有拿到锁";
        }

        int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
        if (stock > 0) {
            int realStock = stock--;
            redisTemplate.opsForValue().set("stock", realStock + "");
            System.out.println("扣减成功，剩余库存" + realStock);
        } else {
            System.out.println("库存不足，扣减失败");
        }
        redisTemplate.delete(lockKey);

        return "end";
    }
}
