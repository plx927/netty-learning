package com.panlingxiao.netty.future;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Created by panlingxiao on 2016/12/2.
 */
public class FutureBadCase {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup childGroup = new NioEventLoopGroup(1);
        final EventExecutorGroup eventExecutors = new DefaultEventExecutorGroup(1);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap().channel(NioServerSocketChannel.class);
            serverBootstrap .group(bossGroup, childGroup);

            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(eventExecutors,new FutureTestHandler());
                }
            });
            ChannelFuture future = serverBootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }

    }

    static class FutureTestHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //返回的结果为DefaultChannelPromise
            ChannelFuture channelFuture = ctx.channel().close();
            channelFuture.await();
        }
    }
}
