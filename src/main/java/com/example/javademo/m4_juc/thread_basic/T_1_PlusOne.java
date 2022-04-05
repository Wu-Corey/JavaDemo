package com.example.javademo.m4_juc.thread_basic;

import java.util.concurrent.CountDownLatch;

/**
 * 2021/5/30
 */
public class T_1_PlusOne {

    private static long N = 0L;

    public static void main(String[] args) throws InterruptedException {
        // 100个线程
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);

        for (int i = 0;i <threads.length;i++){
            threads[i] = new Thread(() -> {
                for(int j = 0;j < 10000;j++){
                    // 如果不上锁会导致多个线程同时改变0->1，最后实际到不了100000
                    synchronized (T_1_PlusOne.class){ // 小括号里：lock/monitor
                        // 大括号里的部分：不可再分，临界区 Critical Section
                        N++;
                    }
                }
                latch.countDown();
            });
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        latch.await();

        System.out.println(N);
    }
}
