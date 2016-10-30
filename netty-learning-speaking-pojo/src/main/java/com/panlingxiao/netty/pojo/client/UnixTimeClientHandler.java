package com.panlingxiao.netty.pojo.client;

import com.panlingxiao.netty.pojo.UnixTime;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by panlingxiao on 2016/10/30.
 */
public class UnixTimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UnixTime unixTime = (UnixTime) msg;
        System.out.println("客户端接受时间:"+unixTime);

    }
}
