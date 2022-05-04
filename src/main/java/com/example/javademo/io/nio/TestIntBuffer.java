package com.example.javademo.io.nio;

import java.nio.IntBuffer;

/**
 * 2022/5/2
 */
public class TestIntBuffer {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        // 读写翻转
        intBuffer.flip();

        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
