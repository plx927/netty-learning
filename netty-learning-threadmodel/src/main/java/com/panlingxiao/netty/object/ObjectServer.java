package com.panlingxiao.netty.object;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

/**
 * Created by panlingxiao on 2016/12/6.
 * 测试Netty4中的线程模型
 * 当Handler在Netty中业务线程中执行时,如果回写一个对象,
 * 那么回写的过程是异步的,因为最后的写操作会进入到IO线程中执行.
 * 此时如果修改对象信息,就会导致输出对象结果
 * 与写的时候不一致。
 */
public class ObjectServer {

    public static void main(String[] args) throws Exception{
        final DefaultEventExecutorGroup eventExecutors = new DefaultEventExecutorGroup(8);
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(bossGroup, childGroup);
            serverBootstrap.handler(new LoggingHandler(LogLevel.DEBUG));

            serverBootstrap.childOption(ChannelOption.ALLOCATOR,PooledByteBufAllocator.DEFAULT);

            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new ObjectEncoder());
                    pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                    pipeline.addLast(eventExecutors, new ObjectServerHandler());
                    //pipeline.addLast( new ObjectServerHandler());
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
            eventExecutors.shutdownGracefully();
        }


    }
}
