package com.example.javademo.m5_jvm.p_1_classLoader.init;

/**
 * 2022/4/16
 */
public class ConstClass {

    static {
        System.out.println("ConstClass init");
    }

    public static final String HELLO_WORLD = "hello world";
}
