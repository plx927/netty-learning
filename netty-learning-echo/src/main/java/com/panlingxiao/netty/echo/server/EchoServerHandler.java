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
        try {
            while (buf.isReadable()) {
                System.out.println((char) buf.readByte());
            }
            //将ByteBuf的读指针重置
            buf.resetReaderIndex();
            ctx.writeAndFlush(buf);
        } finally {
            if(buf.refCnt() > 0){
                ReferenceCountUtil.release(buf);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
