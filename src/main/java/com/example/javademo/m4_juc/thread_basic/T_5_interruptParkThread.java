package com.example.javademo.m4_juc.thread_basic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * 2021/9/11
 */
@Slf4j
public class T_5_interruptParkThread {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            log.info("thread start");
            LockSupport.park();
            log.info("unpark...");
            log.info("打断状态{}", Thread.currentThread().isInterrupted());
        });
        thread.start();
        thread.interrupt();
    }
}
