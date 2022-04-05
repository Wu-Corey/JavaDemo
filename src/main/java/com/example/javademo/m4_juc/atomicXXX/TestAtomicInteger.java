package com.example.javademo.m4_juc.atomicXXX;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * 2022/3/20
 */
@Slf4j
public class TestAtomicInteger {



    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(0);
        System.out.println(i.incrementAndGet());
        System.out.println(i.get());
        System.out.println(i.getAndIncrement());
        System.out.println(i.get());

        System.out.println(i.addAndGet(2));
        System.out.println(i.get());
        System.out.println(i.getAndAdd(2));
        System.out.println(i.get());

        //
        i = new AtomicInteger(1);
        System.out.println(i.updateAndGet(x -> x * 50));

        System.out.println(cusUpdateAndGet(i,x -> x * 50));
    }

    public static int cusUpdateAndGet(AtomicInteger i, IntUnaryOperator operator){
        int prev ;
        int next ;
        do {
            prev = i.get();
            next = operator.applyAsInt(prev);
        }while (!i.compareAndSet(prev,next));

        return next;
    }
}
