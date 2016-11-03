package com.panlingxiao.netty.codec.delimiter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: panlingxiao
 * @Date: 2016/10/31 0031
 * @Description:
 */
public class DelimiterEchoServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DelimiterEchoServerHandler.class);
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String request = (String) msg;
        LOGGER.info("服务器端接收到请求:{},当前数量:{}", request,COUNTER.incrementAndGet());
        ctx.writeAndFlush(String.format("%s$_", request));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("客户端断开连接");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
