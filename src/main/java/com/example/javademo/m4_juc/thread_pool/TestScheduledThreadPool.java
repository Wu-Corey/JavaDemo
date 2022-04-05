package com.example.javademo.m4_juc.thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 2022/3/27
 */
@Slf4j
public class TestScheduledThreadPool {

    public static void main(String[] args) {
//        test01();
//        test02();
//        test03();

        test04();
    }

    /**
     * 测试延迟执行
     */
    public static void test01(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

        log.info("开始");
        pool.schedule(()-> log.info("执行..."),1, TimeUnit.SECONDS);
    }

    /**
     * 测试定时执行
     */
    public static void test02(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

        log.info("开始");
        pool.scheduleAtFixedRate(()-> {
            log.info("执行...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },2,1, TimeUnit.SECONDS);
    }

    /**
     * 测试定时执行-任务结束时刻开始算
     */
    public static void test03(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

        log.info("开始");
        pool.scheduleWithFixedDelay(()-> {
            log.info("执行...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },2,1, TimeUnit.SECONDS);
    }

    /**
     * 测试每周四18:00定时执行
     */
    public static void test04(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime targetTime = now.with(DayOfWeek.THURSDAY).withHour(18).withMinute(0).withSecond(0).withNano(0);


        if (now.compareTo(targetTime) > 0){
            targetTime =  targetTime.plusWeeks(1);
        }
        long initialDelay = Duration.between(now,targetTime).toMillis();
        long period = 60 * 60 *24 * 7 * 1000;

        log.info("开始");
        pool.scheduleAtFixedRate(()-> {
            log.info("执行...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },initialDelay,period, TimeUnit.SECONDS);
    }
}
