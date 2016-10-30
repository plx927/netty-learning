package com.panlingxiao.netty.discard.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by panlingxiao on 2016/10/29.
 * ChannelHandler用于处理由Netty所产生的IO事件
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Discard the received data silently.
       // ((ByteBuf) msg).release(); // (3)

        ByteBuf buf = (ByteBuf) msg;
        try{
            //判断Buf是否可读,如果可读则依次读取
            while (buf.isReadable()){
                System.out.print((char) buf.readByte());
            }
        }finally {
            //显示地释放引用计数对象
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
