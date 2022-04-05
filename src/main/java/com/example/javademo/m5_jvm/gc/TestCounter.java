package com.example.javademo.m5_jvm.gc;

/**
 * 2022/2/8
 * 测试引用计数
 * 加上如下参数打印日志
 * -XX:+PrintGCDetails
 */
public class TestCounter {

    static class Student{
        private Object instance;

        private static final int _1MB = 1024 * 1024;

        private static byte[] b = new byte[20 * _1MB];
    }

    public static void main(String[] args) {
        Student studentA = new Student();
        Student studentB = new Student();
        studentA.instance = studentB;
        studentB.instance = studentA;

        studentA = null;
        studentB = null;

        System.gc();
    }
}
