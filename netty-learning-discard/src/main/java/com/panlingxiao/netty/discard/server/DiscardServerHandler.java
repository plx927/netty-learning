package com.panlingxiao.netty.discard.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by panlingxiao on 2016/10/29.
 * ChannelHandler用于处理由Netty所产生的IO事件
 * 在默认情况下,如果使用
 * <code>
 *     pipeline.addLast(new DiscardServerHandler());
 * </code>
 * 的方式，对于不同的连接都会创建一个新的ChannelHandler。
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {


    private static final Logger LOGGER = LoggerFactory.getLogger(DiscardServerHandler.class);

    public DiscardServerHandler(){
        LOGGER.info("DiscardServerHandler Created:{}",this);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Discard the received data silently.
       // ((ByteBuf) msg).release(        ); // (3)
        LOGGER.info("ctx:{},channel:{}",ctx,ctx.channel());
        ByteBuf buf = (ByteBuf) msg;
        try{
            //判断Buf是否可读,如果可读则依次读取接受到的每一个字节
            while (buf.isReadable()){
                System.out.print((char) buf.readByte());
            }
            System.out.println("\r");
        }finally {
            //显示地释放引用计数对象
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("channelReadComplete");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("chanelInactive");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("channelUnregistered");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("channel active success");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("channel register success");
    }
}
