package com.example.javademo.m4_juc.atomicXXX;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 2021/5/30
 */
public class T_1_AtomicInteger {

    AtomicInteger count =  new AtomicInteger(0);

    void m(){
        for (int i = 0; i < 10000; i++) {
            count.incrementAndGet();    // count++
        }
    }

    public static void main(String[] args) {
        T_1_AtomicInteger t = new T_1_AtomicInteger();

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    t.m();
                }
            },"thread=" + i));
        }

        threads.forEach((o) -> o.start());

        threads.forEach((o) ->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println();
    }
}
