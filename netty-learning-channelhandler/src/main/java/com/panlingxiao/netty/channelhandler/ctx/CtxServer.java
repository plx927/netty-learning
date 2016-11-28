package com.panlingxiao.netty.channelhandler.ctx;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: panlingxiao
 * @Date: 2016/11/27 0027
 * @Description: 测试Ctx事件传播
 */
public class CtxServer {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                /**
                 * This method will be called once the {@link Channel} was registered. After the method returns this instance
                 * will be removed from the {@link ChannelPipeline} of the {@link Channel}.
                 *
                 * @param ch the {@link Channel} which was registered.
                 * @throws Exception is thrown if an error occurs. In that case it will be handled by
                 *                   {@link #exceptionCaught(ChannelHandlerContext, Throwable)} which will by default close
                 *                   the {@link Channel}.
                 */
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new CtxHandler());
                }
            });

            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
