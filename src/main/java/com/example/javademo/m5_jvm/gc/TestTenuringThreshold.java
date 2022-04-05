package com.example.javademo.m5_jvm.gc;

/**
 * 2022/2/27
 * 进入老年代的阈值-XX:MaxTenuringThreshold
 * -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 */
public class TestTenuringThreshold {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] a1,a2,a3;
        a1 = new byte[_1MB / 4];
        a2 = new byte[4 * _1MB];
        a3 = new byte[4 * _1MB];
        a3 = null;
        a3 = new byte[4 * _1MB];
    }
}
