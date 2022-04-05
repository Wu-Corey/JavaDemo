package com.example.javademo.util;

/**
 * 2022/3/27
 */
public class SleepHelper {

    public static void sleep(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
