package com.example.javademo.m4_juc.thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 2022/3/26
 */
@Slf4j
public class TestInvokeAll {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        List<Future<String>> futures = executorService.invokeAll(
                Arrays.asList(
                        () -> {
                            log.info("任务1开始");
                            Thread.sleep(1000);
                            return "1";
                        },
                        () -> {
                            log.info("任务2开始");
                            Thread.sleep(500);
                            return "2";
                        },
                        () -> {
                            log.info("任务3开始");
                            Thread.sleep(2000);
                            log.info("最晚结束的任务结束了");
                            return "3";
                        }
                )
                // 这里可以传超时时间，到了超时时间之后就直接返回已经执行完的任务的结果，没执行的或正在执行的中断
        );

        log.info("所有任务都结束了，主线程才能走到这里");
        futures.forEach(f ->{
            try {
                log.info(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
