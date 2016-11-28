package com.panlingxiao.netty.channelhandler.context;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by panlingxiao on 2016/11/12.
 * ChannelHandlerContext测试
 */

public class ChannelHandlerContextServer {


    private static FactorialHandler factorialHandler = new FactorialHandler();

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new ByteToIntegerDecoder());
                    /*
                     * 同时添加两个相同ChannelHandler,该ChannelHandler必须使用@Shareable来标识
                     * Netty底层会对添加的ChannelHandler进行检测，如果发现是相同的ChannelHandler
                     * 并且没有使用@Shareable来进行标识，那么在添加ChannelHandler的时候会报错
                     */
                    pipeline.addLast(factorialHandler);
                    pipeline.addLast(factorialHandler);
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
