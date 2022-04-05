package com.example.javademo.m4_juc.thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 2022/3/26
 */
@Slf4j
public class TestSubmit {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<String> future = executorService.submit(() -> {
            log.info("running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ok";
        });

        log.info("阻塞在这里，等有结果了才能拿到...");
        log.info("有结果了，阻塞才结束，拿到结果{}",future.get());
    }
}
