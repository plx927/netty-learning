package com.panlingxiao.netty.codec.delimiter;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.net.InetSocketAddress;

/**
 * @Author: panlingxiao
 * @Date: 2016/10/31 0031
 * @Description:
 */
public class DelimiterEchoClient {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(workerGroup);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
                    pipeline.addLast(new DelimiterBasedFrameDecoder(1024, buf));
                    pipeline.addLast(new StringDecoder());
                    pipeline.addLast(new DelimiterEchoClientHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8080);
            channelFuture.channel().closeFuture().sync();
        } finally {
            System.out.println("客户端关闭线程池");
            workerGroup.shutdownGracefully();
        }

    }


}
