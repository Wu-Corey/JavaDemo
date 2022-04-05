package com.example.javademo.m4_juc.reentrantlock;


import java.util.concurrent.locks.LockSupport;

/**
 * 2022/3/5
 * 交替输出-park/unpark
 */
public class TestAlternateOutPutParkUnpark {

    static Thread t1;
    static Thread t2;
    static Thread t3;

    public static void main(String[] args) {
        P p = new P(10);
        t1 =  new Thread(()->{
            p.print("a",t2);
        });
        t2 =  new Thread(()->{
            p.print("b",t3);
        });
        t3 =  new Thread(()->{
            p.print("c",t1);
        });
        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);
    }
}
class P{
    private int loopCount;

    public P(int loopCount) {
        this.loopCount = loopCount;
    }

    public void print(String s,Thread thread){
        for (int i = 0; i < loopCount; i++) {
            LockSupport.park();
            System.out.print(s);
            LockSupport.unpark(thread);
        }
    }
}
