package com.panlingxiao.netty.codec.delimiter;

import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: panlingxiao
 * @Date: 2016/10/31 0031
 * @Description:
 */
public class DelimiterEchoClientHandler extends SimpleChannelInboundHandler<String>{

    private static final Logger LOGGER = LoggerFactory.getLogger(DelimiterEchoClientHandler.class);

    private static final String request = "hello netty!$_";
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
            for(int i = 0;i < 10;i++){
                ctx.writeAndFlush(request);
            }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        LOGGER.info("客户端接受到数据:{}",msg);
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        LOGGER.info("客户端接受数据完毕，准备开始关闭连接");
//        ChannelFuture future = ctx.close();
//        future.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//                if(future.isSuccess()){
//                    LOGGER.info("客户端关闭连接成功");
//                }
//            }
//        });
//        LOGGER.info("开始关闭连接");
//    }
}
