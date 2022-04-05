package com.example.javademo.m5_jvm.stack;

import java.util.Map;

/**
 * 2022/2/27
 */
public class TestStackTrace {

    public static void main(String[] args) {
        for (Map.Entry<Thread, StackTraceElement[]> threadEntry : Thread.getAllStackTraces().entrySet()) {
            Thread thread = threadEntry.getKey();
            StackTraceElement[] stackTrace = threadEntry.getValue();
            if (thread.equals(Thread.currentThread())){
                // 忽略main线程
                continue;
            }
            System.out.print("\n线程:"+ thread.getName()+"\n");
            for (StackTraceElement stackTraceElement : stackTrace) {
                System.out.print("\t"+ stackTraceElement+"\n");
            }
        }
    }
}
