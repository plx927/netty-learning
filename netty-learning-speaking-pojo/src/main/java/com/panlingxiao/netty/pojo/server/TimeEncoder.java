package com.panlingxiao.netty.pojo.server;

import com.panlingxiao.netty.pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

/**
 * Created by panlingxiao on 2016/10/29.
 * 用于将UnixTime对象转换成ByteBuf
 */
public class TimeEncoder extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, final ChannelPromise promise) throws Exception {
        System.out.println("encode");
        UnixTime unixTime = (UnixTime) msg;
        ByteBuf byteBuf = ctx.alloc().buffer(4);
        byteBuf.writeInt((int) unixTime.value());
        /*
         * 1. 传递promise,因为netty通过promise来标记数据是否写出成功
         * 2. 不用执行flush方法，因为已经有一个单独的方法flush方法，专门用于数据的刷新操作
         */
        ctx.write(byteBuf, promise);
       // promise.addListener(new ChannelPromiseNotifier(promise));

    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        System.out.println("flush");
        super.flush(ctx);
    }
}
