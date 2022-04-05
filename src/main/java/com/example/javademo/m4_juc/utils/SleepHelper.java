package com.example.javademo.m4_juc.utils;

/**
 * 2021/5/31
 */
public  class SleepHelper {

    public static void sleepSecond(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
