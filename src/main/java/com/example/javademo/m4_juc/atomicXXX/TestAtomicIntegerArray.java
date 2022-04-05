package com.example.javademo.m4_juc.atomicXXX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 2022/3/20
 */
public class TestAtomicIntegerArray {

    public static void main(String[] args) {
        test(
                ()->new int[10],
                (array)->array.length,
                (array,index) -> array[index]++,
                array -> System.out.println(Arrays.toString(array))
        );

        test(
                ()->new AtomicIntegerArray(10),
                AtomicIntegerArray::length,
                AtomicIntegerArray::getAndIncrement,
                System.out::println
        );
    }

    public static <T> void test(
            Supplier<T> arraySupplier,
            Function<T,Integer> lengthFun,
            BiConsumer<T,Integer> putConsumer,
            Consumer<T> printConsumer
    ){
        List<Thread> threads = new ArrayList<>();
        T array = arraySupplier.get();
        Integer len = lengthFun.apply(array);
        for (int i = 0; i < len; i++) {
            threads.add(
                    new Thread(()->{
                        for (int j = 0; j < 10000; j++) {
                            putConsumer.accept(array,j%len);
                        }
                    })
            );
        }

        threads.forEach(Thread::start);
        // 等所有线程都结束
        threads.forEach(t->{
           try {
               //只要t线程没有退出，主线程就会wait()
               t.join();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
        });
        printConsumer.accept(array);
    }

}
