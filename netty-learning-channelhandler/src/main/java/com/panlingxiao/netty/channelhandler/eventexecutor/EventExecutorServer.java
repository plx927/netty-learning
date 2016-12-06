package com.panlingxiao.netty.channelhandler.eventexecutor;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by panlingxiao on 2016/12/5.
 */
public class EventExecutorServer {
    public static void main(String[] args) throws Exception {


        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup childGroup = new NioEventLoopGroup();
        final DefaultEventExecutorGroup executorGroup = new DefaultEventExecutorGroup(2);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap().channel(NioServerSocketChannel.class);
            serverBootstrap.group(bossGroup, childGroup);
            serverBootstrap.handler(new LoggingHandler(LogLevel.DEBUG));
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(executorGroup,
                            new StringEncoder(),
                            new StringDecoder(), new MyHandler());
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }


    /**
     * 因为加了在添加ChannelPipeline的时候，指定了特定的EventExecutorGroup,因此
     */
    static class MyHandler extends SimpleChannelInboundHandler<String> {

        static final Logger log = LoggerFactory.getLogger(MyHandler.class);

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            log.debug("channelRegistered,channel'eventloop is :{},ctx's eventloop is {}:",
                    ctx.channel().eventLoop(), ctx.executor());
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.debug("channelActive,channel's eventLoop is :{},ctx's eventloop is {}:",
                    ctx.channel().eventLoop(), ctx.executor());
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            log.debug("channelRead,channel's eventLoop is :{},ctx's eventloop is {}:",
                    ctx.channel().eventLoop(), ctx.executor());
        }
    }
}
