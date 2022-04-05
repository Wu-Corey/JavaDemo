package com.example.javademo.m4_juc.refTypeAndThreadLocal;

import java.lang.ref.SoftReference;

/**
 * 2021/5/31
 * 软引用实际上是一个对象包装
 */
public class T_1_SoftReference {

    public static void main(String[] args) {
        // m->强引用指向包装对象，里面软引用指向10M的字节数组
        SoftReference<byte[]> m =  new SoftReference<>(new byte[1024* 1024 * 10]);
        System.out.println(m.get());
        // 建议GC回收
        System.gc();

        try {
            // 留时间回收
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(m.get());

        // -Xmx20M 把堆大小设置为20M，JVM占一定内存，剩下不足10M
        // 再申请分配一个10M的字节数组，强引用，heap将装不下，系统进行垃圾回收，先回收一次，如果不够，回收软引用
//        byte[] bytes = new byte[1024 * 1024 * 8];
        SoftReference<byte[]> n =  new SoftReference<>(new byte[1024* 1024 * 9]);
        System.out.println(m.get());
    }




}
