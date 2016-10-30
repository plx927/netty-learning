package com.panlingxiao.netty.stream.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: panlingxiao
 * @Date: 2016/10/30 0030
 * @Description:
 */
public class StreamServerHandler extends ChannelInboundHandlerAdapter {

    private AtomicInteger counter = new AtomicInteger();

    private static final String QUERY_TIME_ORDER = "Query Time Order";
    private static final String BAD_ORDER = "Bad Order";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        //设置接收到字节的长度
        int length = buf.readableBytes();
        byte[] req = new byte[length];
        //将读取的字节读取到缓冲区中
        buf.readBytes(req);
        String body = new String(req, "UTF-8").substring(0,req.length - System.getProperty("line.separator").length());
        System.out.println("Server receive order:" + body + ",counter is: " + counter.incrementAndGet());
        String response = QUERY_TIME_ORDER.equalsIgnoreCase(body) ? new Date().toString() : BAD_ORDER;
        response = response + System.getProperty("line.separator");

        byte[] data = response.getBytes("UTF-8");
//        ByteBuf buffer = ctx.alloc().buffer(data.length);
        ByteBuf buffer = Unpooled.buffer(data.length);
        buffer.writeBytes(data);
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端建立连接");
    }


}
