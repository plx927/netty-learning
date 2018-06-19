package com.panlingxiao.netty.handler;

import com.panlingxiao.netty.client.NettyRemotingClient;
import com.panlingxiao.netty.protocol.Message;
import com.panlingxiao.netty.service.ResponseFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by panlingxiao on 2018/6/20.
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<Message> {

    private NettyRemotingClient client;

    public ClientHandler(NettyRemotingClient client) {
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        log.info("receive response,responseId:{}", msg.getHeader().getRequestId());
        ResponseFuture responseFuture = client.removeResponse(msg.getHeader().getRequestId());
        if (responseFuture != null) {
            responseFuture.putResponse(msg.getBody());
        }
    }
}
