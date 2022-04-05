package com.example.javademo.m4_juc.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 2022/2/27
 * 锁超时
 */
@Slf4j
public class TestOverTime {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            log.info("尝试获取锁");
            try {
                if (!lock.tryLock(2, TimeUnit.SECONDS)){
                    log.info("尝试获取锁失败");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.info("打断异常 获取锁失败");
                return;
            }
            try {
                log.info("获取到锁了");
            }finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        log.info("获取到锁");
        t1.start();
//        t1.interrupt();  如果等待过程中打断了，抛异常

    }
}
