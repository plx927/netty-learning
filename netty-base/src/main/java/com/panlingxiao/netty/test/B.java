package com.panlingxiao.netty.test;

import com.panlingxiao.netty.handler.BHandler;
import com.panlingxiao.netty.server.NettyRemotingServer;

/**
 * Created by panlingxiao on 2018/6/20.
 */
public class B {

    public static void main(String[] args) throws Exception {
        NettyRemotingServer nettyRemotingServer = new NettyRemotingServer(8080,new BHandler());
        nettyRemotingServer.start();
    }
}
