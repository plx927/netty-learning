/*
 * Copyright 2013-2018 Lilinfeng.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.panlingxiao.netty.protocol.netty.codec;

import com.panlingxiao.netty.protocol.netty.struct.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;
import java.util.Map;


/**
 * 消息编码器,将响应结果转换成字节流
 */
public final class NettyMessageEncoder extends
        MessageToByteEncoder<NettyMessage> {

    MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() throws IOException {
        this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg,
                          ByteBuf sendBuf) throws Exception {
        if (msg == null || msg.getHeader() == null)
            throw new Exception("The encode message is null");

        //将消息头先写入到ByteBuf中
        sendBuf.writeInt((msg.getHeader().getCrcCode()));
        sendBuf.writeInt((msg.getHeader().getLength()));
        sendBuf.writeLong((msg.getHeader().getSessionID()));
        sendBuf.writeByte((msg.getHeader().getType()));
        sendBuf.writeByte((msg.getHeader().getPriority()));

        //写入消息头的附件的键值对的个数
        sendBuf.writeInt((msg.getHeader().getAttachment().size()));
        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes("UTF-8");
            //写入每个key的长度
            sendBuf.writeInt(keyArray.length);
            //写入每个key的内容
            sendBuf.writeBytes(keyArray);
            value = param.getValue();
            //对value进行编解码,写入value的长度和value的内容
            marshallingEncoder.encode(value, sendBuf);
        }
        key = null;
        keyArray = null;
        value = null;
        if (msg.getBody() != null) {
            //对body进行编解码，写入每个body的长度以及内容
            marshallingEncoder.encode(msg.getBody(), sendBuf);
        } else {
            //如果Body没有内容,则将Body的长度设置为0
            sendBuf.writeInt(0);
        }
        //设置消息的长度,消息长度字段在ByteBuf第4个位置开始
        sendBuf.setInt(4, sendBuf.readableBytes() - 8);
    }
}
