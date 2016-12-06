package com.panlingxiao.netty.object;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by panlingxiao on 2016/12/6.
 */
public class ObjectClientHandler extends SimpleChannelInboundHandler<User>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, User msg) throws Exception {
        System.out.println(msg.getUsername());
        ctx.close();
    }
}
