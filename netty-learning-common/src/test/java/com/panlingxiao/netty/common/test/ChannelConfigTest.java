package com.panlingxiao.netty.common.test;

import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.junit.Test;

/**
 * @Author: panlingxiao
 * @Date: 2016/12/6 0006
 * @Description:
 */
public class ChannelConfigTest {

    @Test
    public void testAllocator(){
        NioServerSocketChannel nioServerSocketChannel = new NioServerSocketChannel();
        ServerSocketChannelConfig config = nioServerSocketChannel.config();
        System.out.println(config.getOptions());
    }
}
