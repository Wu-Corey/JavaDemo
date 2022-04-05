package com.example.javademo.m4_juc.wait_notify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 2021/5/30
 * 线程交替输出
 * ReentrantLock+condition实现
 * 加CountDownLatch 实现t2比t1先执行
 */
public class T_1_AlternatePrint {

    public static void main(String[] args) {
        char[] arr1 = "1234567".toCharArray();
        char[] arr2 = "ABCDEFG".toCharArray();
        Lock lock = new ReentrantLock();
        //CountDownLatch latch = new CountDownLatch(1);
        // 队列
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        new Thread(() -> {
           /*
            try {
                // 先锁上t1，等latch.countDown会叫醒
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
            lock.lock();
            try {
                for (char c : arr1) {
                    System.out.print(c);
                    // 唤醒队列2里的
                    condition2.signal();
                    // 当前t1线程进入队列1等待
                    condition1.await();
                }

                condition2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1"
        ).start();

        new Thread(() -> {
            lock.lock();
            try {
                for (char c : arr2) {
                    System.out.print(c);
                    //latch.countDown();
                    condition1.signal();
                    condition2.await();
                }
                condition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();
    }
}
