package com.example.javademo.m5_jvm.method_area;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * 2022/5/1
 * 测试方法区OOM
 * -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
 */
public class TestOOM extends ClassLoader {

    public static void main(String[] args) {
        int i = 0;
        try {
            TestOOM testOOM = new TestOOM();
            for (i = 0; i < 10000; i++) {
                ClassWriter classWriter = new ClassWriter(0);
                classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC,"class"+i,null,"java/lang/Object",null);
                byte[] bytes = classWriter.toByteArray();

                testOOM.defineClass("class"+i,bytes,0,bytes.length);
            }
        }finally {
            System.out.println(i);
        }
    }
}
