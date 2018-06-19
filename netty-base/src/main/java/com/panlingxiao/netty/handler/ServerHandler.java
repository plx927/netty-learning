package com.panlingxiao.netty.handler;

import com.panlingxiao.netty.protocol.Header;
import com.panlingxiao.netty.protocol.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by panlingxiao on 2018/6/20.
 */
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<Message> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        Header header = msg.getHeader();
        log.info("Receive message,requestId:{},body:{}", header.getRequestId(), msg.getBody());
        header.setType(1);
        Message message = new Message(header,"Welcome!");
        ctx.writeAndFlush(message);
    }
}
