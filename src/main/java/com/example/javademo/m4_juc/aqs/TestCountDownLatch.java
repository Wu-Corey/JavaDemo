package com.example.javademo.m4_juc.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static jodd.util.ThreadUtil.sleep;

/**
 * 2022/4/9
 */
@Slf4j
public class TestCountDownLatch {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(10);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        Random r = new Random();
        String[] arr = new String[10];
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            threadPool.submit(()->{
                for (int j = 0; j <= 100; j++) {
                    sleep(r.nextInt(100));

                    arr[finalI] = j + "%";
                    System.out.print("\r"+ Arrays.toString(arr));
                }
                latch.countDown();
            });
        }

        latch.await();
        System.out.println("\n"+"游戏开始");
        threadPool.shutdown();
    }
}
