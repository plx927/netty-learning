package com.panlingxiao.netty.handler;

import com.panlingxiao.netty.client.NettyRemotingClient;
import com.panlingxiao.netty.protocol.Header;
import com.panlingxiao.netty.protocol.Message;
import com.panlingxiao.netty.service.NettyConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by panlingxiao on 2018/6/20.
 */
@Slf4j
public class BHandler extends SimpleChannelInboundHandler<Message> {

    private NettyRemotingClient nettyRemotingClient;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive");
        nettyRemotingClient = new NettyRemotingClient(new NettyConfig());
        nettyRemotingClient.start();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        Header header = msg.getHeader();
        log.info("B Receive message,requestId:{},body:{}", header.getRequestId(), msg.getBody());
        header.setType(1);

        // B调用C
        String result = nettyRemotingClient.invokeSync("127.0.0.1:8001", "Welcome", 3000);
        header.setType(1);
        Message message = new Message(header, "B is " + result);
        ctx.writeAndFlush(message);
    }
}
