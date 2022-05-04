package com.example.javademo.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 2022/5/3
 */
public class TestScatteringAndGathering {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        SocketChannel socketChannel = serverSocketChannel.accept();
        int msgLen = 8;
        while (true) {
            int readLen = 0;
            while (readLen < msgLen) {
                long read = socketChannel.read(byteBuffers);
                readLen += read;

                System.out.println("当前读取到的长度 readLen: " + readLen);
            }

            Arrays.stream(byteBuffers).forEach(System.out::println);

            Arrays.stream(byteBuffers).forEach(Buffer::flip);

            int writeLen = 0;

            while (writeLen < msgLen) {
                long write = socketChannel.write(byteBuffers);
                writeLen += write;

                System.out.println("当前写入的长度 writeLen: " + writeLen);
            }

            Arrays.stream(byteBuffers).forEach(Buffer::clear);

            System.out.println("readLen: " + readLen + "writeLen: " + writeLen + "msgLen: " + msgLen);
        }
    }
}
