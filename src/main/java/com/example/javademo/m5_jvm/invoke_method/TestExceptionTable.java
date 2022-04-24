package com.example.javademo.m5_jvm.invoke_method;

/**
 * 2022/4/23
 */
public class TestExceptionTable {

    public static void main(String[] args) {

        try {
            int a = 1/0;
        }catch (Exception e){
            System.out.println("error");
        }
    }
}
