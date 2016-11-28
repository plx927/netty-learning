package com.panlingxiao.netty.future;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @Author: panlingxiao
 * @Date: 2016/11/28 0028
 * @Description: 任务中抛出异常,通过Future判断执行结果
 */
public class NettyFuture2 {

    public static void main(String[] args) {

        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Future<String> future = eventExecutors.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(1);
                throw  new RuntimeException("异步任务执行发生异常");
            }
        });

        future.addListener(new GenericFutureListener<Future<? super String>>() {
            @Override
            public void operationComplete(Future<? super String> future) throws Exception {
                System.out.println("is success: "+future.isSuccess());
                System.out.println("is Done : "+future.isDone());
                future.cause().printStackTrace();
            }
        });

    }
}
