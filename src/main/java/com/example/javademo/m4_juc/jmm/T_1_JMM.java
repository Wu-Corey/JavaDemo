package com.example.javademo.m4_juc.jmm;

/**
 * 2021/6/28
 */
public class T_1_JMM {

    private static volatile boolean initFlag = false;

    public static void main(String[] args) throws InterruptedException {

        new Thread(()->{
            System.out.println("等待数据...");

            while (!initFlag){
            }
            System.out.println("初始化成功");
        }).start();

        Thread.sleep(2000);

        new Thread(()->{
            System.out.println("准备数据...");
            initFlag = true;
            System.out.println("数据准备好了...");
        }).start();
    }
}
