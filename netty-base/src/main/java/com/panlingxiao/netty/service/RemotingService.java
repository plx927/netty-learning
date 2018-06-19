package com.panlingxiao.netty.service;

import io.netty.channel.Channel;

/**
 * Created by panlingxiao on 2018/6/20.
 */
public interface RemotingService<T> {

    void start();

    void shutdown();

    T invokeSync(String address, T t, long timeout) throws Exception ;

    void invokeAsync(Channel channel, T t, InvokeCallback invokeCallback);
}
