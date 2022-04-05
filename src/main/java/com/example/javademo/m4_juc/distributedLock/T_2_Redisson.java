package com.example.javademo.m4_juc.distributedLock;


import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2021/6/24
 */
@Controller
@RequestMapping("test/v2")
public class T_2_Redisson {

    @Autowired
    private Redisson redisson;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("deduct_lock")
    public String deductLock() {

        String lockKey = "lockKey";
        RLock lock = redisson.getLock(lockKey);

        try {
            lock.lock();

            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock--;
                redisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功，剩余库存" + realStock);
            } else {
                System.out.println("库存不足，扣减失败");
            }
        }finally {
            lock.unlock();
        }
        return "end";
    }
}
