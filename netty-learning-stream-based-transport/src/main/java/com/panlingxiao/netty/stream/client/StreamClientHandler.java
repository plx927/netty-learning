package com.panlingxiao.netty.stream.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: panlingxiao
 * @Date: 2016/10/30 0030
 * @Description:
 */
public class StreamClientHandler extends ChannelInboundHandlerAdapter{

    private AtomicInteger counter = new AtomicInteger();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] message = ("Query Time Order"+System.getProperty("line.separator")).getBytes("UTF-8");
        for (int i = 0;i < 100;i++){
            ByteBuf buffer = Unpooled.buffer(message.length);
            buffer.writeBytes(message);
            Thread.sleep(100);
            ctx.writeAndFlush(buffer);
        }
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        int length = buf.readableBytes();
        System.out.println(length);
        byte[] req = new byte[length];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("Now is :"+body+",counter is:"+ counter.incrementAndGet());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
