package com.panlingxiao.netty.codec.line;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.net.InetSocketAddress;

/**
 * @Author: panlingxiao
 * @Date: 2016/10/31 0031
 * @Description:
 */
public class LineBasedFrameServer {


    public static void main(String[] args) throws Exception {
        final ChannelInitializer<SocketChannel> channelInitializer = new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                /**
                 * 1.设置每帧的最大字节数
                 * 2.指定编解码器是否去除掉结尾分隔符
                 * 3.当字节长度大于每帧的最大长度时，则抛出异常。
                 */
                ch.pipeline().addLast(new LineBasedFrameDecoder(Integer.MAX_VALUE, true, false));
                ch.pipeline().addLast(new LineBasedFrameServerHandler());
            }
        };

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.childHandler(channelInitializer);
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8080)).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
