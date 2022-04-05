package com.example.javademo.m4_juc.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

import static com.example.javademo.m4_juc.utils.SleepHelper.sleepSecond;

/**
 * 2022/4/5
 */
@Slf4j
public class TestSemaphore {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {

            new Thread(()->{
                log.info("start...");
                sleepSecond(1);

                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    log.info("running...");
                    sleepSecond(1);
                    log.info("end!");
                }finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}
