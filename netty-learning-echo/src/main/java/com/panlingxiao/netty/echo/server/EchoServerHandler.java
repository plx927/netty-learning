package com.panlingxiao.netty.echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by panlingxiao on 2016/10/29.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    public EchoServerHandler() {
        System.out.println("EchoServerHandler Created");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        while (buf.isReadable()) {
            System.out.print((char) buf.readByte());
        }
        System.out.println("\n");
        //将ByteBuf的读指针重置
        buf.resetReaderIndex();
        //Netty底层将ByteBuf释放的时候会自动将Buf进行释放
        ctx.writeAndFlush(buf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
