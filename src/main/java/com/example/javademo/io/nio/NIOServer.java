package com.example.javademo.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 2022/5/3
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
        serverSocketChannel.socket().bind(inetSocketAddress);

        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){

            if (selector.select(1000) == 0){
                System.out.println("服务端没有发现连接...");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();

                if (selectionKey.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("连接建立..."+socketChannel);

                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));


                }

                if (selectionKey.isReadable()){
                    SocketChannel socketChannel =  (SocketChannel)selectionKey.channel();

                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();

                    socketChannel.read(buffer);

                    System.out.println("接收到msg:  "+ new String(buffer.array()));
                }

                iterator.remove();
            }
        }
    }
}

class NIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        if (!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("客户端发起了连接请求，连接暂时没有建立完成，不会阻塞，可以做其他事...");
            }
        }

        String msg = "hello world";
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

        socketChannel.write(buffer);

        System.in.read();
    }
}
