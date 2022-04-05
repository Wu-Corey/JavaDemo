package com.example.javademo.m4_juc.refTypeAndThreadLocal;

import java.lang.ref.WeakReference;

/**
 * 2021/5/31
 */
public class T_2_WeakReference {

    public static void main(String[] args) {
        WeakReference<M> m = new WeakReference<>(new M());
        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());
    }
}
