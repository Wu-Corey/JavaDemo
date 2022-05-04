package com.example.javademo.io.nio.group_chat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 2022/5/3
 */
public class GroupChatClient {

    private SocketChannel socketChannel;
    private Selector selector;
    private final Integer PORT = 6666;
    private final String HOST = "127.0.0.1";
    private String userName;


    public GroupChatClient() {
        try {

            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_READ);
            userName = socketChannel.getLocalAddress().toString().substring(1);

            System.out.println(userName+" is ok...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(String msg){
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receive(){

        try {
            if (selector.select() > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();

                    if (selectionKey.isReadable()){
                        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();

                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.read(buffer);

                        System.out.println(new String(buffer.array()));
                    }

                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatClient groupChatClient = new GroupChatClient();

        new Thread(()->{
            while (true){
                groupChatClient.receive();
            }

        },"readThread").start();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()){
            groupChatClient.send(scanner.nextLine());
        }
    }
}
