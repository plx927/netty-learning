package com.panlingxiao.netty.future;

import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by panlingxiao on 2016/11/5.
 */
public class NettyFuture {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyFuture.class);

    public static void main(String[] args) {
        NioEventLoopGroup executors = new NioEventLoopGroup();
        final int num1 = 1;
        final int num2 = 2;

        Future<Integer> future = executors.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(3);
                return num1 + num2;
            }
        });
        System.out.println(future);
        System.out.println("start");


        future.addListener(new FutureListener<Integer>() {
            @Override
            public void operationComplete(Future<Integer> future) throws Exception {
                if (future.isDone() && future.isSuccess()) {
                    Integer result = future.get();
                    LOGGER.info("result:{}", result);
                }
            }
        });
        System.out.println("end");
    }
}
