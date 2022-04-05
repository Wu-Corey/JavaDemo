package com.example.javademo.m4_juc.thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 2022/3/26
 */
@Slf4j
public class TestInvokeAny {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Object o = executorService.invokeAny(
                Arrays.asList(
                        () -> {
                            log.info("任务1开始");
                            Thread.sleep(1000);
                            return "1";
                        },
                        () -> {
                            log.info("任务2开始");
                            Thread.sleep(500);
                            log.info("最早结束的任务结束了");
                            return "2";
                        },
                        () -> {
                            log.info("任务3开始");
                            Thread.sleep(2000);
                            return "3";
                        }
                )
        );

        log.info("只要有任务结束，主线程就能走到这里");
        log.info(o.toString());
    }

}
