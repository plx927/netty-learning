package com.panlingxiao.netty.channelhandler.handlerflow;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.Charset;

/**
 * @Author: panlingxiao
 * @Date: 2016/11/27 0027
 * @Description:
 */
public class InboundHandlerA implements ChannelInboundHandler {


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println(getClass().getName()+" channelRegistered");
        ctx.fireChannelRegistered();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(getClass().getName()+" channelActive");
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf buf = (ByteBuf) msg;
            String str = buf.toString(Charset.forName("UTF-8"));
            System.out.println(getClass()+",channelRead,msg:"+str +"，refCnt:"+buf.refCnt());
            ctx.fireChannelRead(str);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println(getClass()+" channelReadComplete");
        ctx.fireChannelReadComplete();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println(getClass()+" userEventTriggered");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println(getClass()+" channelWritabilityChanged");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println(getClass()+"exceptionCaught");
        ctx.fireExceptionCaught(cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    }

}