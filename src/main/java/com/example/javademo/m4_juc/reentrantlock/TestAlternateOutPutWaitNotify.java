package com.example.javademo.m4_juc.reentrantlock;

import lombok.extern.slf4j.Slf4j;

/**
 * 2022/3/5
 * 交替输出-wait/notify实现
 */
@Slf4j
public class TestAlternateOutPutWaitNotify {

    public static void main(String[] args) {
        Obj o = new Obj(1, 10);

        new Thread(()->{
            o.print("a",1,2);
        },"t1").start();
        new Thread(()->{
            o.print("b",2,3);
        },"t2").start();
        new Thread(()->{
            o.print("c",3,1);
        },"t3").start();
    }

}

class Obj{
    public Obj(int flag, int loopCount) {
        this.flag = flag;
        this.loopCount = loopCount;
    }

    private int flag;
    private int loopCount;

    public void print(String s,int waitFlag,int nextFlag){
        for (int i = 0; i < loopCount; i++) {
            synchronized (this){
                while (flag != waitFlag){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(s);
                flag = nextFlag;
                notifyAll();
            }
        }
    }
}
