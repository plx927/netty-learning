package com.panlingxiao.netty.codec;

import com.panlingxiao.netty.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by panlingxiao on 2018/6/20.
 */
@Slf4j
public class MsgEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        int messageLength = msg.getBody().getBytes().length + 16;
        out.writeInt(messageLength);
        out.writeInt(msg.getHeader().getMagicNum());
        out.writeInt(msg.getHeader().getType());
        out.writeInt(msg.getHeader().getRequestId());
        out.writeBytes(msg.getBody().getBytes());
        //log.info("msgLength:{},bodyLength:{}", messageLength, msg.getBody().getBytes().length);

    }
}
