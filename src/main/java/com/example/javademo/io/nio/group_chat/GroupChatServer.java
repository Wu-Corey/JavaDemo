package com.example.javademo.io.nio.group_chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 2022/5/3
 */
public class GroupChatServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private final Integer PORT = 6666;

    public GroupChatServer() {
        try {
            this.serverSocketChannel = ServerSocketChannel.open();
            this.serverSocketChannel.configureBlocking(false);
            this.selector = Selector.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(){
        System.out.println("listening...");
        while (true){
            try {
                if (selector.select() != 0){
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()){
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            System.out.println(socketChannel.getRemoteAddress() +"上线了");

                            socketChannel.register(selector,SelectionKey.OP_READ);
                        }

                        if (key.isReadable()){
                            SocketChannel socketChannel = null;
                            try {
                                socketChannel = (SocketChannel) key.channel();
                                receiveAndTransformMsg(socketChannel);

                            }catch (Exception e){

                            }
                        }
                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void receiveAndTransformMsg(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        if (socketChannel.read(buffer) > 0){
            String msg = new String(buffer.array());
            System.out.println("接收到 "+socketChannel.getRemoteAddress()+" 的消息： "+msg);

            System.out.println("开始转发消息...");
            Iterator<SelectionKey> iterator = selector.keys().iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                SelectableChannel channel = selectionKey.channel();

                try {
                    if (channel instanceof SocketChannel && channel != socketChannel){

                        SocketChannel destChannel =  (SocketChannel)channel;

                        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());

                        destChannel.write(byteBuffer);

                        System.out.println("转发消息给 "+ destChannel.getRemoteAddress());

                    }
                }catch (Exception e){
                    System.out.println(socketChannel.getRemoteAddress()+"离线了");
                    selectionKey.cancel();
                    channel.close();
                }
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();

        groupChatServer.listen();
    }
}
