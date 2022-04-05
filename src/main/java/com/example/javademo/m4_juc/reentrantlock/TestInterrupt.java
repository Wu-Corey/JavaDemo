package com.example.javademo.m4_juc.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 2022/2/27
 */
@Slf4j
public class TestInterrupt {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                log.info("等待被打断了");
                e.printStackTrace();
            }
        }, "t1");

        lock.lock();
        log.info("主线程加锁");
        t1.start();

        try {
            log.info("主线程开始执行打断");
            t1.interrupt();
        }finally {
            lock.unlock();
        }
    }
}

