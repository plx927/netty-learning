package com.panlingxiao.netty.codec.line;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: panlingxiao
 * @Date: 2016/10/31 0031
 * @Description:
 */
public class LineBasedFrameServerHandler extends ChannelInboundHandlerAdapter {

    private static final String QUERY_TIME_ORDER = "query time order";
    private static final String BAD_ORDER = "Bad Order";

    private static final Logger LOGGER = LoggerFactory.getLogger(LineBasedFrameServerHandler.class);
    private static final AtomicInteger COUNTER = new AtomicInteger();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf reqBuf = (ByteBuf) msg;
        byte[] requestBytes = new byte[reqBuf.readableBytes()];
        reqBuf.readBytes(requestBytes);
        String str = new String(requestBytes, "UTF-8");
        LOGGER.info("服务器端第{}条数据:{}", COUNTER.incrementAndGet(),str);
        String response = String.format("%s%s", QUERY_TIME_ORDER.equalsIgnoreCase(str) ? new Date().toString() : BAD_ORDER, System.getProperty("line.separator"));
        byte[] responseBytes = response.getBytes("UTF-8");
        ByteBuf respBuf = ctx.alloc().buffer(requestBytes.length);
        respBuf.writeBytes(responseBytes);
        ChannelFuture channelFuture = ctx.writeAndFlush(respBuf);
        //添加异步回调的通知
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isDone()) {
                    if (future.isSuccess()) {
                        LOGGER.debug("服务器返回数据成功");
                    }
                }
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("发生异常:",cause);
        ctx.close();
    }
}
