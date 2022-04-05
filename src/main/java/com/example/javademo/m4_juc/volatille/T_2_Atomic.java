package com.example.javademo.m4_juc.volatille;

/**
 * 2021/6/29
 * volatile为什么不能保证原子性
 */
public class T_2_Atomic {

    private static volatile int num = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread[] threads = new Thread[10];
        for (int i = 0;i < 10;i++){
            threads[i] =  new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    num += 1;
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            // 让父线程等待子线程结束才继续线程
            thread.join();
        }
        System.out.println(num);    // 结果小于等于10000
    }
}
