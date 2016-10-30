package com.panlingxiao.netty.time.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by panlingxiao on 2016/10/29.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        final ByteBuf time = ctx.alloc().buffer(4);
        int value = (int)(System.currentTimeMillis() / 1000L+2208988800L);
        //将数据写入到Buffer中
        time.writeInt(value);
        //回写数据给客户端
        final ChannelFuture f = ctx.writeAndFlush(time);
        f.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                assert  f == future;
                ctx.close();
                System.out.println("断开客户断连接");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
