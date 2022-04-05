package com.example.javademo.m4_juc.locksupport;

import com.example.javademo.m4_juc.utils.SleepHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * 2022/2/26
 */
@Slf4j
public class TestParkUnpark {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            SleepHelper.sleepSecond(3);
            log.info("park...");
            LockSupport.park();
            log.info("resume...");
        }, "t1");
        t1.start();

        SleepHelper.sleepSecond(2);
        new Thread(()->{
            log.info("unpark");
            LockSupport.unpark(t1);
        },"t2").start();
    }
}
