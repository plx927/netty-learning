package com.panlingxiao.netty.channelhandler.eventloop;

import io.netty.channel.*;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * Created by panlingxiao on 2016/11/5.
 */
public class EventLoopTaskServerHandler extends SimpleChannelInboundHandler<String> {

    private static Logger LOGGER = LoggerFactory.getLogger(EventLoopTaskServerHandler.class);

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final String msg) throws Exception {
        final EventExecutor executor = ctx.executor();
        System.out.println(executor);
        LOGGER.info("服务器接受到数据:{}", msg);
        //将业务处理放入到EventLoop中来执行
        Future<Void> future = executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Thread.sleep(3000);
                ctx.writeAndFlush(new Date() + "---" + msg);
                return null;
            }
        });
        System.out.println(future);
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isDone() && future.isSuccess()) {
                    LOGGER.info("服务器端处理数据完成");
                }
            }
        });

        /**
        bussinessThreadPool.executor(new Runnable()){
                 处理业务逻辑
                //将回写逻辑再放到IO线程中来处理
                ctx.eventExecutor().submit(new WriteTask(){

              })
        }
         */

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
