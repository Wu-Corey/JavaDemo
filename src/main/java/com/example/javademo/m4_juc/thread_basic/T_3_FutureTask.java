package com.example.javademo.m4_juc.thread_basic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 2021/9/8
 */
@Slf4j(topic = "t3_futureTask")
public class T_3_FutureTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("running...");
                Thread.sleep(1000);
                return 100;
            }
        });
        Thread t1 = new Thread(task,"t1");
        t1.start();
        t1.join();

        // 返回前会一直阻塞，直到sleep完，然后返回结果，才能get到这个返回值
        log.debug("{}",task.get());
    }
}
