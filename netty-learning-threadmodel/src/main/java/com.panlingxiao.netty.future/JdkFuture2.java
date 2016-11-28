package com.panlingxiao.netty.future;

import java.util.concurrent.*;

/**
 * @Author: panlingxiao
 * @Date: 2016/11/28 0028
 * @Description:
 */
public class JdkFuture2 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(2);
                return null;
            }
        });

        boolean cancelResult = future.cancel(true);
        System.out.println(cancelResult);

        //使用JDK的Future我们只能知道一个异步是否已经结束,但是我们不知道是如何结束的。
        boolean done = future.isDone();
        System.out.println(done);


    }
}
