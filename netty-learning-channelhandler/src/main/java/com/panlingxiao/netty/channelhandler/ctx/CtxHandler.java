package com.panlingxiao.netty.channelhandler.ctx;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.nio.charset.Charset;

/**
 * @Author: panlingxiao
 * @Date: 2016/11/27 0027
 * @Description:
 */
public class CtxHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(buf.toString(Charset.forName("UTF-8"))+","+buf);
        /*
         * 在读取玩数据后,将读取事件继续向后传播,数据会被ChannelHandler的Netty中的DefaultChannelPipeline$TailContext所丢弃
         * 并且以日志的方式进行记录。
         */
        //ctx.fireChannelRead(buf);
        //ctx.writeAndFlush(buf);
        ctx.writeAndFlush("abc").addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                System.out.println(future.cause());
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause);
        ctx.close();
    }
}
