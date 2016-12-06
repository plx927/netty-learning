package com.panlingxiao.netty.future;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by panlingxiao on 2016/11/5.
 * 分析JDK的Future存在的问题
 */
public class JdkFuture {

    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws Exception {
        final int num1 = 1;
        final int num2 = 2;

        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(3);
                return num1 + num2;
            }
        });
        System.out.println("开始获取结果:"+new Date().getTime() / 1000);

        /*
         * 最大的问题:
         * 一旦结果没有执行完成，那么线程必须等待结果完成才能继续执行
         */
        Integer result = future.get();
        System.out.println("result:"+result);
        System.out.println("获取结果结束:"+new Date().getTime() / 1000);

    }
}
