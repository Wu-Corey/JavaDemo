package com.example.javademo.io.nio;



import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 2022/5/2
 */
public class TestFileChannel {

    public static void main(String[] args) throws IOException {
//        write();

//        read();

//        copy();

        copyV2();
    }

    private static void read() throws IOException {
        File file = new File("d:\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());

        fileChannel.read(buffer);

        System.out.println(new String(buffer.array()));
    }

    private static void write() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01.txt");

        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        String str = "hello world";

        buffer.put(str.getBytes());

        buffer.flip();

        fileChannel.write(buffer);

        fileOutputStream.close();
    }

    /**
     * 通过buffer
     * @throws IOException
     */
    private static void copy() throws IOException {
        File file = new File("d:\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01_back.txt");
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(2);

        while (true){
            //第一次读到2个字节已经把buffer占满，position=limit，如果不clear把position归0，下次循环就无法read到值了
            buffer.clear();
            int read = fileInputStreamChannel.read(buffer);
            System.out.println("read="+read);
            if (read == -1){
                break;
            }
            buffer.flip();
            outputStreamChannel.write(buffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

    /**
     * 直接channel拷贝
     * @throws IOException
     */
    private static void copyV2() throws IOException {
        File file = new File("d:\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01_back.txt");
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();


        fileOutputStreamChannel.transferFrom(fileInputStreamChannel,0,fileInputStreamChannel.size());


        fileInputStreamChannel.close();
        fileOutputStreamChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
    }

}
