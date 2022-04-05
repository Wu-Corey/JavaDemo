package com.example.javademo.m4_juc.refTypeAndThreadLocal;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 2021/5/31
 */
public class T_3_PhantomReference {

    private static final List<Object> LIST = new ArrayList<>();
    private static final ReferenceQueue<M> QUEUE = new ReferenceQueue<>();

    public static void main(String[] args) {
        PhantomReference<M> phantomReference = new PhantomReference<>(new M(), QUEUE);
        System.out.println(phantomReference.get()); // 这里是get不到的
        ByteBuffer b = ByteBuffer.allocateDirect(1024);

        // 不断申请分配内存的线程
        new Thread(() -> {
            while (true){
                LIST.add(new byte[1024 * 1024]);
                System.out.println(phantomReference.get());
            }
        }).start();

        // 追踪垃圾回收线程
        new Thread(() ->{
            while (true){
                Reference<? extends M> poll = QUEUE.poll();
                if (poll != null){
                    // 引用被回收会加进队列 一旦队列里有对象
                    System.out.println("虚引用对象被JVM回收" + poll);
                }
            }
        }).start();
    }
}
