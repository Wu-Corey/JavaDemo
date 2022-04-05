package com.example.javademo.m4_juc.reentrantlock;


import com.example.javademo.m4_juc.utils.SleepHelper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 2022/3/5
 * 交替输出-ReentrantLock
 */
public class TestAlternateOutPutReentrantLock {

    public static void main(String[] args) {
        Lock lock = new Lock(10);
        Condition a = lock.newCondition();
        Condition b = lock.newCondition();
        Condition c = lock.newCondition();

        new Thread(()->{
            lock.print("a",a,b);
        }).start();
        new Thread(()->{
            lock.print("b",b,c);
        }).start();
        new Thread(()->{
            lock.print("c",c,a);
        }).start();

        // 刚开始三个线程都会进入等待 所以需要主线程来驱动
        SleepHelper.sleepSecond(1);
        lock.lock();
        try {
            a.signal();
        }finally {
            lock.unlock();
        }
    }
}

class Lock extends ReentrantLock{

    private int loopCount;

    public Lock(int loopCount) {
        this.loopCount = loopCount;
    }

    public void print(String s, Condition cur,Condition next){
        for (int i = 0; i < loopCount; i++) {
            lock();
            try {
                cur.await();
                System.out.print(s);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}
