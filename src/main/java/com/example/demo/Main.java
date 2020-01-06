package com.example.demo;

import io.netty.buffer.ByteBuf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 杨帮东 (qq:397827222)
 * @version 1.0
 * @date 2020/01/04 01:06
 **/
public class Main {

    public static void main(String[] args) throws IOException {
       System.out.println(Runtime.getRuntime().availableProcessors());
    }

    private static void nio() throws IOException {
        FileInputStream inputStream = new FileInputStream("text.txt");
        FileOutputStream outputStream = new FileOutputStream("out.txt");
        FileChannel inputStreamChannel = inputStream.getChannel();
        FileChannel outputStreamChannel = outputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        while (true) {
            byteBuffer.clear();

            int read = inputStreamChannel.read(byteBuffer);
            System.out.println("read:" + read);
            if(-1 == read) {
                break;
            }

            byteBuffer.flip();
            outputStreamChannel.write(byteBuffer);
        }
        inputStreamChannel.close();
        outputStreamChannel.close();
    }

}
