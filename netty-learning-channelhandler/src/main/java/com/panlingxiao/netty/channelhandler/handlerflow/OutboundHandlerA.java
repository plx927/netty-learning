package com.panlingxiao.netty.channelhandler.handlerflow;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * @Author: panlingxiao
 * @Date: 2016/11/27 0027
 * @Description:
 */
public class OutboundHandlerA implements ChannelOutboundHandler {

    private static final Logger log = LoggerFactory.getLogger(OutboundHandlerA.class);

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        log.info("bind,socketAddress:{}",localAddress);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        log.info("connect,remoteAddress:{}",remoteAddress);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {

    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {

    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {

    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        log.info("read");
        ctx.read();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("writeMsg,msg is :{}",msg);
        String str = (String) msg;
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes(str.getBytes());
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        log.info("flush");
        ctx.flush();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
