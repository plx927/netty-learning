package com.panlingxiao.netty.test;

import com.panlingxiao.netty.handler.ServerHandler;
import com.panlingxiao.netty.server.NettyRemotingServer;

/**
 * Created by panlingxiao on 2018/6/20.
 */
public class C {

    public static void main(String[] args) throws Exception {
        NettyRemotingServer nettyRemotingServer = new NettyRemotingServer(8001,new ServerHandler());
        nettyRemotingServer.start();
    }
}
