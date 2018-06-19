package com.panlingxiao.netty.client;

import com.panlingxiao.netty.codec.MsgDecoder;
import com.panlingxiao.netty.codec.MsgEncoder;
import com.panlingxiao.netty.handler.ClientHandler;
import com.panlingxiao.netty.handler.ServerHandler;
import com.panlingxiao.netty.protocol.Header;
import com.panlingxiao.netty.protocol.Message;
import com.panlingxiao.netty.service.InvokeCallback;
import com.panlingxiao.netty.service.NettyConfig;
import com.panlingxiao.netty.service.RemotingService;
import com.panlingxiao.netty.service.ResponseFuture;
import com.panlingxiao.netty.util.RemotingUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by panlingxiao on 2018/6/20.
 */
@Slf4j
public class NettyRemotingClient implements RemotingService<String> {

    private static AtomicInteger REQUEST_ID = new AtomicInteger();

    private EventLoopGroup worker;
    private int connectTimeOut;
    private int sendBuf;
    private int recvBuf;
    private Bootstrap bootstrap;

    /**
     * This map caches all on-going requests.
     * Key is requestId
     */
    protected final ConcurrentMap<Integer, ResponseFuture> responseTable =
            new ConcurrentHashMap<>(256);


    /**
     * Address is key
     */
    private final ConcurrentMap<String, Channel> channelTables = new ConcurrentHashMap<>();


    public NettyRemotingClient(NettyConfig nettyConfig) {
        this.connectTimeOut = nettyConfig.getConnectTimeoutMillis();
        this.sendBuf = nettyConfig.getClientSocketSndBufSize();
        this.recvBuf = nettyConfig.getClientSocketRcvBufSize();
        this.bootstrap = new Bootstrap();
    }


    @Override
    public void start() {
        worker = new NioEventLoopGroup();
        bootstrap.group(worker).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeOut)
                .option(ChannelOption.SO_SNDBUF, sendBuf)
                .option(ChannelOption.SO_RCVBUF, recvBuf)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 假设Header占8个字节
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, -4, 0))
                                .addLast(new MsgDecoder())
                                .addLast(new MsgEncoder())
                                .addLast(new ClientHandler(NettyRemotingClient.this));

                    }
                });

    }

    @Override
    public void shutdown() {
        for (Map.Entry<String, Channel> entry : channelTables.entrySet()) {
            Channel channel = entry.getValue();
            if (Objects.nonNull(channel)) {
                channel.close().addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        log.info("close channel,remoting address:{},result:{}", channel.remoteAddress(), future.isSuccess());
                    }
                });
            }
        }
        channelTables.clear();
        worker.shutdownGracefully();
    }

    @Override
    public String invokeSync(String address, String msg, long timeout) throws Exception {
        if (Objects.isNull(address)) {
            log.error("address is null");
            return null;
        }
        Channel channel = getAndCreateChannel(address);
        if (channel != null) {
            int requestId = REQUEST_ID.incrementAndGet();
            try {
                final ResponseFuture<String> responseFuture = new ResponseFuture<>(requestId, timeout, null);
                this.responseTable.put(requestId, responseFuture);

                Header header = new Header();
                header.setRequestId(requestId);
                Message message = new Message(header, msg);

                channel.writeAndFlush(message).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isSuccess()) {
                            // 发送消息成功
                            responseFuture.setSendRequestOK(true);
                            return;
                        }

                        // 发送消息失败
                        responseTable.remove(requestId);
                        responseFuture.setCause(future.cause());
                        responseFuture.putResponse(null);
                        log.warn("send a request command to channel <" + address + "> failed.");
                    }
                });

                // 同步等待
                String response = responseFuture.waitResponse(timeout);
                if (null == response) {
                    if (responseFuture.isSendRequestOK()) {
                        throw new RuntimeException("Time out ");
                    } else {
                        throw new RuntimeException("send msg fail");
                    }
                }
                return response;
            } finally {
                responseTable.remove(requestId);
            }
        }

        return null;
    }

    private Channel getAndCreateChannel(String address) throws InterruptedException {
        Channel channel = channelTables.get(address);
        if (Objects.nonNull(channel)) {
            return channel;
        }
        synchronized (this) {
            channel = channelTables.get(address);
            if (Objects.nonNull(channel)) {
                return channel;
            }
            // 由于channel的连接是异步的，所以当返回ChannelFuture，此时未必已经完成,因此需要同步等待
            ChannelFuture channelFuture = this.bootstrap.connect(RemotingUtil.string2SocketAddress(address)).sync();
            if (channelFuture.isSuccess()) {
                log.info("Connect {} success,", address);
                channelTables.put(address, channelFuture.channel());
                return channelFuture.channel();
            }
        }
        return null;
    }


    @Override
    public void invokeAsync(Channel channel, String o, InvokeCallback invokeCallback) {

    }


    public ResponseFuture removeResponse(int requestId) {
        return responseTable.remove(requestId);
    }
}


