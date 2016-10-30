package com.panlingxiao.netty.stream.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author: panlingxiao
 * @Date: 2016/10/30 0030
 * @Description:
 */
public class StreamServer {

    public static void main(String[] args) throws Exception{
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class)
                            .group(bossGroup,workerGroup)
                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ch.pipeline().addLast(new StreamServerHandler());
                                }
                            });
            ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(8080));
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                        if(future.isSuccess()){
                            System.out.println("服务器绑定端口成功");
                        }
                }
            });
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
