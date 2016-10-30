package com.panlingxiao.netty.pojo.server;

import com.panlingxiao.netty.pojo.UnixTime;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by panlingxiao on 2016/10/29.
 */
public class UnixTimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UnixTime unixTime = new UnixTime();
        System.out.println("服务器发送时间:"+unixTime);
        ChannelFuture future = ctx.writeAndFlush(unixTime);
        future.addListener(ChannelFutureListener.CLOSE);
        System.out.println("write UnixTime");
    }
}
