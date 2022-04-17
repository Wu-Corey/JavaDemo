package com.example.javademo.m5_jvm.p_1_classLoader.init;

/**
 * 2022/4/16
 */
public class SubClass extends SuperClass {

    static {
        System.out.println("SubClass init");
    }
}
