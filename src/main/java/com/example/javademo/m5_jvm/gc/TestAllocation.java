package com.example.javademo.m5_jvm.gc;

/**
 * 2022/2/26
 * 参数 -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+UseSerialGC
 * Eden:10M，其中9M可用
 * Serial+Serial Old组合
 */
public class TestAllocation {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] a1,a2,a3,a4,a5;
        a1 = new byte[2 * _1MB];
        a2 = new byte[2 * _1MB];
        a3 = new byte[2 * _1MB]; // 出现一次Minor GC
        a4 = new byte[4 * _1MB];
        a5 = new byte[2 * _1MB];
    }


}
