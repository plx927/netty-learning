package com.panlingxiao.netty.util;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by panlingxiao on 2018/6/20.
 */
public class RemotingUtil {

    public static SocketAddress string2SocketAddress(final String addr) {
        String[] s = addr.split(":");
        InetSocketAddress isa = new InetSocketAddress(s[0], Integer.parseInt(s[1]));
        return isa;
    }
}
