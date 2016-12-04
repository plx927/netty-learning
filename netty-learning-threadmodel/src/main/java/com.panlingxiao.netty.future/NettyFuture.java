package com.panlingxiao.netty.future;

import io.netty.util.concurrent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by panlingxiao on 2016/11/5.
 */
public class NettyFuture {

    private static final Logger log = LoggerFactory.getLogger(NettyFuture.class);

    public static void main(String[] args) {
        final EventExecutorGroup executors = new DefaultEventExecutorGroup(1);
        final int num1 = 1;
        final int num2 = 2;

        Future<Integer> future = executors.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(3);
                int result = num1 + num2;
                log.info("计算结果:{}",result);
                return result;
            }
        });
        System.out.println(future);
        System.out.println("start");


        future.addListener(new FutureListener<Integer>() {
            @Override
            public void operationComplete(Future<Integer> future) throws Exception {
                if (future.isSuccess()) {
                    Integer result = future.get();
                    log.info("任务执行成功,计算结果:{}", result);
                    executors.shutdownGracefully();
                } else if (future.isCancellable()) {
                    log.info("任务被取消");
                } else {
                    log.error("任务执行失败:", future.cause());
                }
            }
        });
        System.out.println("end");
        future.cancel(true);
    }
}
