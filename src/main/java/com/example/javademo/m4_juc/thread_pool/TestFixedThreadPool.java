package com.example.javademo.m4_juc.thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 2022/3/26
 */
@Slf4j
public class TestFixedThreadPool {

    public static void main(String[] args) {
        // 传入自定义的线程工厂，创建自己命名的线程
        ExecutorService threadPool = Executors.newFixedThreadPool(2, (r) -> {
            AtomicInteger atomicInteger = new AtomicInteger(1);

            return new Thread(r, "myPool_" + atomicInteger.getAndIncrement());
        });

        for (int i = 1; i <= 3; i++) {
            int finalI = i;
            threadPool.execute(()-> log.info(String.valueOf(finalI)));
        }
    }
}
