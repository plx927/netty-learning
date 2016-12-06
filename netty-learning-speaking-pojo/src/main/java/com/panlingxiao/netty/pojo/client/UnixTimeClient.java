package com.panlingxiao.netty.pojo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by panlingxiao on 2016/10/30.
 */
public class UnixTimeClient {
    public static void main(String[] args) throws Exception{
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        String host = "localhost";
        int port = 8080;
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class)
                    .group(workerGroup)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeDecoder()).addLast(new UnixTimeClientHandler());
                        }
                    }).option(ChannelOption.SO_KEEPALIVE, true); // (4)
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            System.out.println(channelFuture.isSuccess());
            //channelFuture.channel().closeFuture().sync();
        } finally {
                workerGroup.shutdownGracefully();
        }

    }
}
