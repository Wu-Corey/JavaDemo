package com.example.javademo.m4_juc.aqs;


import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.LockSupport;

/**
 * 2021/7/1
 * 自定义AQS实现
 */
public class MyLock {

    private volatile int state = 0;

    private Thread lockHolder;

    // 要用线程安全的队列作为等待队列,基于CAS实现
    // linkedBlockedQueue基于AQS实现，不能用
    private ConcurrentLinkedDeque<Thread> waiters = new ConcurrentLinkedDeque<>();

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Thread getLockHolder() {
        return lockHolder;
    }

    public void setLockHolder(Thread lockHolder) {
        this.lockHolder = lockHolder;
    }

    public void lock() {
        Thread currentThread = Thread.currentThread();
        if (acquire()) {
            return;
        }
        waiters.add(currentThread);
        // 自旋
        for (; ; ) {
            // 队列里第一个线程才能抢锁
            if (currentThread == waiters.peek() && acquire()) {
                // 队列头线程拿到锁 踢出等待队列
                waiters.poll();
                return;
            }
            // 阻塞当前线程 放弃CPU使用权
            LockSupport.park();
        }
    }

    public void unLock() {
        if (Thread.currentThread() != lockHolder) {
            throw new RuntimeException("lockHolder is not current thread");
        }

        if (compareAndSwapState(getState(), 0)) {
            setLockHolder(null);
            // 唤醒队列里第一个线程
            Thread first = waiters.peek();
            if (first != null) {
                LockSupport.unpark(first);
            }
        }
    }

    // 是否能加锁成功
    private boolean acquire() {
        Thread currentThread = Thread.currentThread();
        if (getState() == 0) { // 同步器尚未被持有
            // 没人排队/自己是队列头，才能去尝试原子操作改变state
            if ((waiters.size() == 0 || currentThread == waiters.peek()) && compareAndSwapState(0, 1)) {
                setLockHolder(currentThread);
                return true;
            }
        }
        return false;
    }


    // 利用Unsafe类实现原子操作改变值
    public final boolean compareAndSwapState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    private static final Unsafe unsafe = reflectGetUnsafe();

    // 偏移量
    private static final long stateOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset(MyLock.class.getDeclaredField("state"));
        } catch (Exception e) {
            throw new Error();
        }
    }

    // 反射获取Unsafe类
    private static Unsafe reflectGetUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            return null;
        }
    }
}
