package com.panlingxiao.netty.object;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;

/**
 * Created by panlingxiao on 2016/12/6.
 */
public class ObjectClient {

    public static void main(String[] args) throws Exception{
        NioEventLoopGroup childGroup = new NioEventLoopGroup(1);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(childGroup);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                    pipeline.addLast(new ObjectEncoder());
                    pipeline.addLast(new ObjectClientHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("localhost", 8080)).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            childGroup.shutdownGracefully();
        }
    }
}
