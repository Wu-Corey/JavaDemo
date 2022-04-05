package main.java.com.example.javademo.m4_juc.dead_lock;

import com.example.javademo.m4_juc.utils.SleepHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * 2022/2/27
 */
@Slf4j
public class TestDeadLock {

    public static void main(String[] args) {

        Object A = new Object();
        Object B = new Object();

        new Thread(()->{
            synchronized (A){
                log.info("t1获取到A锁");
                SleepHelper.sleepSecond(1);
                synchronized (B){
                    log.info("t1获取到B锁");
                }
            }
        },"t1").start();

        new Thread(()->{
            synchronized (B){
                log.info("t2获取到A锁");
                SleepHelper.sleepSecond(1);
                synchronized (A){
                    log.info("t2获取到B锁");
                }
            }
        },"t2").start();
    }
}
