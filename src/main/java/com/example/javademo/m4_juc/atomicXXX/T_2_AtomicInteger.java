package com.example.javademo.m4_juc.atomicXXX;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * 2021/5/30
 */
public class T_2_AtomicInteger {
    int num = 0;
    AtomicInteger atomicInteger = new AtomicInteger();

    LongAdder longAdder = new LongAdder();

    public void increase() {
        longAdder.increment();
    }

    public long getNum() {
        return longAdder.longValue();
    }


//    public  void  increase(){
//        atomicInteger.incrementAndGet();
//    }
//    public long getNum(){
//        return atomicInteger.get();
//    }


}
