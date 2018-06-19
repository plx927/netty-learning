package com.panlingxiao.netty.codec;

import com.panlingxiao.netty.protocol.Header;
import com.panlingxiao.netty.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by panlingxiao on 2018/6/20.
 */
@Slf4j
public class MsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 读取整个包的长度
        int msgLength = in.readInt();
        int bodyLength = msgLength - 16;
       // log.info("msgLength:{},bodyLength:{}", msgLength, bodyLength);

        int magicNum = in.readInt();
        int type = in.readInt();
        int requestId = in.readInt();

        Header header = new Header();
        header.setMagicNum(magicNum);
        header.setType(type);
        header.setRequestId(requestId);

        byte[] data = new byte[bodyLength];
        in.readBytes(data);
        out.add(new Message(header, new String(data)));
    }
}
