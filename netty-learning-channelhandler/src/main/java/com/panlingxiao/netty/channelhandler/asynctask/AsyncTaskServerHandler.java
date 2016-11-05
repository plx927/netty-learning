package com.panlingxiao.netty.channelhandler.asynctask;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by panlingxiao on 2016/11/5.
 */
public class AsyncTaskServerHandler extends SimpleChannelInboundHandler<String>{

    static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncTaskServerHandler.class);

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final String msg) throws Exception {
        LOGGER.info("服务器收到数据:{}", msg);
        executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                TimeUnit.SECONDS.sleep(3);
                String response = new Date().toString() + msg;
                //ctx.writeAndFlush(response);
                LOGGER.info("服务器返回数据:{}",response);
                return null;
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        LOGGER.info("服务器关闭连接");
    }

    static class WriteTask implements Runnable{
        private ChannelHandlerContext ctx;
        public WriteTask(ChannelHandlerContext ctx){
            this.ctx = ctx;
        }
        @Override
        public void run() {

        }
    }

}
