package com.panlingxiao.netty.server;

import com.panlingxiao.netty.codec.MsgDecoder;
import com.panlingxiao.netty.codec.MsgEncoder;
import com.panlingxiao.netty.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * Created by panlingxiao on 2018/6/20.
 */
@Slf4j
public class NettyRemotingServer {


    private final ServerBootstrap serverBootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private int port;
    private EventLoopGroup defaultEventExecutorGroup;

    private Channel channel;

    private ChannelHandler channelHandler;

    public NettyRemotingServer(int port,ChannelHandler channelHandler) {
        this.port = port;
        this.serverBootstrap = new ServerBootstrap();
        boss = new NioEventLoopGroup(1);
        worker = new NioEventLoopGroup();
        defaultEventExecutorGroup = new DefaultEventLoopGroup();
        this.channelHandler = channelHandler;
    }

    public void start() throws Exception {
        this.serverBootstrap.group(this.boss, this.worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.SO_KEEPALIVE, false)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                new LengthFieldBasedFrameDecoder(1024, 0, 4, -4, 0))
                                .addLast(new MsgDecoder())
                                .addLast(new MsgEncoder())
                                .addLast(channelHandler);
                    }
                });

        ChannelFuture channelFuture = this.serverBootstrap.bind().sync();
        this.channel = channelFuture.channel();
        log.info("Server bind success,port is:{}", port);
    }


    public void shutDown() {
        this.boss.shutdownGracefully();
        this.worker.shutdownGracefully();
        this.defaultEventExecutorGroup.shutdownGracefully();
    }



}
