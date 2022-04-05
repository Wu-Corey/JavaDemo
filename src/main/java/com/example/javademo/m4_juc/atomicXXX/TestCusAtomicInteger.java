package com.example.javademo.m4_juc.atomicXXX;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 2022/3/20
 * 自定义AtomicInteger
 */
public class TestCusAtomicInteger {

    public static void main(String[] args) {
        MyAtomicInteger myAtomicInteger = new MyAtomicInteger(0);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(()->{
                for (int j = 0; j < 10000; j++) {
//                    myAtomicInteger.threadUnsafeIncrement(1);
                    myAtomicInteger.increment(1);
                }
            }));
        }

        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(myAtomicInteger.getValue());
    }


}
class MyAtomicInteger{
    private volatile int value;
    static Long valueOffSet;

    static Unsafe UNSAFE;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);

            valueOffSet = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public Integer threadUnsafeIncrement(int x){
        value += x;
        return value;
    }

    public Integer increment(int x){
        int prev;int next;
        while (true){
            prev = getValue();
            next = prev + x;

            if(UNSAFE.compareAndSwapInt(this,valueOffSet,prev,next)){
                break;
            }
        }

        return next;
    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

