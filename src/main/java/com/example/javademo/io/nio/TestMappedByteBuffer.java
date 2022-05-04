package com.example.javademo.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 2022/5/3
 * 直接在堆外内存修改文件
 */
public class TestMappedByteBuffer {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:\\file01.txt","rw");
        FileChannel channel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE,0,5);

        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(4, (byte) 'd');

        randomAccessFile.close();

        // 修改结果：hello world -> Helld world
    }
}
