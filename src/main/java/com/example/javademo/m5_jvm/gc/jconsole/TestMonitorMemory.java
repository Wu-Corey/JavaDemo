package com.example.javademo.m5_jvm.gc.jconsole;

import java.util.ArrayList;
import java.util.List;

/**
 * 2022/3/6
 * -Xms100m -Xmx100m -XX:+UseSerialGC
 */
public class TestMonitorMemory {

    static class OOMObject{
        public byte[] placeHolder = new byte[64 * 1024];
    }

    public static void main(String[] args) throws InterruptedException {
        fillHeap(1000);
        System.out.println("执行完了方法");
        Thread.sleep(100000);
    }

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Thread.sleep(50);
           list.add( new OOMObject());
        }
        System.gc();
    }
}
