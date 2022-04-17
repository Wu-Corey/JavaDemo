package com.example.javademo.m5_jvm.structrue.pc;

/**
 * 2022/4/17
 */
public class TestPCRegister {

    public static void main(String[] args) {
        int a = 0;

        {
            int b = 0;
            b = a + 1;
        }

        int c = a + 1;
    }
}
