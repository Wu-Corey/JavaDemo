package com.example.javademo.m5_jvm.gc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 2022/4/30
 */
public class TestGc {

    byte[] o = new byte[new Random().nextInt(1024 * 1000)];

    public static void main(String[] args) throws InterruptedException {
        List<TestGc> list = new ArrayList<>();

        while (true){
            list.add(new TestGc());

            Thread.sleep(10);
        }
    }
}
