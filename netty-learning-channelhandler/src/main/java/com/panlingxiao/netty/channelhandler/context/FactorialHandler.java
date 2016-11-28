package com.panlingxiao.netty.channelhandler.context;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * Created by panlingxiao on 2016/11/12.
 * 该ChannelHandler会被多个连接使用,但是ChannelHandler所对应ChannelHandlerContext是不同的。
 */
@ChannelHandler.Sharable
public class FactorialHandler extends SimpleChannelInboundHandler<Integer> {

    /**
     * 相同的AttributeKey可以被不同的ChannelHandlerContext使用，这里类似ThreadLocal.
     */
    private static final AttributeKey<Integer> COUNTER = AttributeKey.valueOf("counter");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Integer number) throws Exception {
        Integer a = ctx.attr(COUNTER).get();
        if (a == null) {
            a = 1;
        }else{
            System.out.println(a);
        }
        ctx.attr(COUNTER).set(a * number);
        ctx.fireChannelRead(number);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
