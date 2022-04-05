package com.example.javademo.m6_jmm;

import lombok.extern.slf4j.Slf4j;

/**
 * 2022/3/7
 * 测试可见性
 */
@Slf4j
public class TestVisibility {

    static volatile boolean flag = true;
    public static void main(String[] args) throws InterruptedException {

        new Thread(()->{
            while (flag){
                System.out.println();
            }
            log.info("循环结束");
        },"t1").start();

        Thread.sleep(1000);

        flag = false;
        log.info("主线程改了标记");
    }
}
