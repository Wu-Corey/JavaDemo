package com.example.javademo.m4_juc.refTypeAndThreadLocal;

/**
 * 2021/5/31
 */
public class M {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("被回收了");
    }
}
