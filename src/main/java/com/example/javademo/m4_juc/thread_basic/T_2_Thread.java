package com.example.javademo.m4_juc.thread_basic;

import lombok.extern.slf4j.Slf4j;

/**
 * 2021/9/8
 */
@Slf4j(topic = "T_2")
public class T_2_Thread {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            log.info("线程1...");
        },"thread1");
        t1.start();
        log.info("主线程...");
    }

}
