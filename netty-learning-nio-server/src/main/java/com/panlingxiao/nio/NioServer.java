package com.panlingxiao.nio;

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
 * @Author: panlingxiao
 * @Date: 2016/12/22 0022
 * @Description:
 */
public class NioServer {

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            serverSocketChannel.bind(new InetSocketAddress(8080));

            for(;;){
                int selectNum = selector.select();
                System.out.println("selectNum:"+selectNum);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                for (Iterator<SelectionKey> iterator = selectedKeys.iterator(); iterator.hasNext();){
                    SelectionKey key = iterator.next();
                    System.out.println("key:"+key);
                    iterator.remove();
                    if(key.isValid()) {
                        if (key.isAcceptable()) {
                            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                            SocketChannel socketChannel = serverChannel.accept();
                            socketChannel.configureBlocking(false);
                            System.out.println("注册到ServerSocketChannel:"+ socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocateDirect(1024)));
                            System.out.println("接收到到客户端连接");
                        } else if (key.isReadable()) {
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            ByteBuffer buffer = (ByteBuffer) key.attachment();
                            int num = 0;
                            try {
                                num = socketChannel.read(buffer);
                                //客户端关闭连接
                                if(num == -1){
                                    key.cancel();
                                    socketChannel.close();
                                    System.out.println("客户端断开连接");
                                }else {
                                    buffer.flip();
                                    byte[] data = new byte[buffer.limit()];
                                    buffer.get(data, 0, num);
                                    String msg = new String(data);
                                    System.out.println("服务器收到:" + msg);
                                    buffer.flip();
                                    socketChannel.write(buffer);
                                    buffer.clear();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                key.cancel();
                                socketChannel.close();
                                System.out.println("客户端断开连接");
                            }

                        }
                    }else{
                        key.cancel();
                        key.channel().close();
                        System.out.println("SelectionKey失效,关闭连接");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
