package com.panlingxiao.netty.service;

/**
 * @Author: panlingxiao
 * @Date: 2016/12/14 0014
 * @Description:
 */
public class NettyConfig {

    private int clientSocketSndBufSize = 65535;
    private int clientSocketRcvBufSize = 65535;

    private int connectTimeoutMillis = 3000;

    public int getClientSocketSndBufSize() {
        return clientSocketSndBufSize;
    }

    public void setClientSocketSndBufSize(int clientSocketSndBufSize) {
        this.clientSocketSndBufSize = clientSocketSndBufSize;
    }

    public int getClientSocketRcvBufSize() {
        return clientSocketRcvBufSize;
    }

    public void setClientSocketRcvBufSize(int clientSocketRcvBufSize) {
        this.clientSocketRcvBufSize = clientSocketRcvBufSize;
    }

    public int getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    public void setConnectTimeoutMillis(int connectTimeoutMillis) {
        this.connectTimeoutMillis = connectTimeoutMillis;
    }
}
