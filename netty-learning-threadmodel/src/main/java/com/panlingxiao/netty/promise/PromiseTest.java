package com.panlingxiao.netty.promise;

import io.netty.channel.DefaultChannelPromise;

/**
 * @Author: panlingxiao
 * @Date: 2016/12/4 0004
 * @Description:
 */
public class PromiseTest {

    public static void main(String[] args) {
        DefaultChannelPromise defaultChannelPromise = new DefaultChannelPromise(null);
        boolean result = defaultChannelPromise.isCancellable();
        System.out.println(result);
    }
}
