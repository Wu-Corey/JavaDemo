package com.example.javademo.m5_jvm.stackFrame;

/**
 * 2022/4/23
 */
public class TestLocalVariableTable {

    public static void main(String[] args) {
        {
            byte[] bytes = new byte[1024 * 1024 * 1000];
        }
        int a = 0;
        System.gc();
    }
}
