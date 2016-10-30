package com.panlingxiao.netty.stream.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author: panlingxiao
 * @Date: 2016/10/30 0030
 * @Description:
 */
public class StreamClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(workGroup);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StreamClientHandler());
                }
            });
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(8080)).sync();
            future.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
        }


    }
}
