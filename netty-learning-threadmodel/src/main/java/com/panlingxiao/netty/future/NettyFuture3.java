package com.panlingxiao.netty.future;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @Author: panlingxiao
 * @Date: 2016/11/28 0028
 * @Description:
 */
public class NettyFuture3 {

    public static void main(String[] args) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup(1);
        Future<Void> future = eventExecutors.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                TimeUnit.SECONDS.sleep(3);
                return null;
            }
        });

        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                System.out.println(future.get());
            }
        });

    }
}
