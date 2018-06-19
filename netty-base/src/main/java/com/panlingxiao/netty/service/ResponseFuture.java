package com.panlingxiao.netty.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by panlingxiao on 2018/6/20.
 */
public class ResponseFuture<T> {


    private int requestId;

    private long timeout;
    private InvokeCallback invokeCallback;

    private volatile boolean sendRequestOK = true;
    private volatile Throwable cause;

    /**
     * 同步计数器
     */
    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private T response;


    public ResponseFuture(int requestId, long timeout, InvokeCallback invokeCallback) {
        this.requestId = requestId;
        this.timeout = timeout;
        this.invokeCallback = invokeCallback;
    }

    public boolean isSendRequestOK() {
        return sendRequestOK;
    }

    public void setSendRequestOK(boolean sendRequestOK) {
        this.sendRequestOK = sendRequestOK;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public void putResponse(T response) {
        this.response = response;
        this.countDownLatch.countDown();
    }

    public T waitResponse(long timeoutMillis) throws InterruptedException {
        this.countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
        return this.response;
    }



}
