package com.panlingxiao.netty.object;

import io.netty.channel.*;

/**
 * Created by panlingxiao on 2016/12/6.
 */
public class ObjectServerHandler extends SimpleChannelInboundHandler<User> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, User msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        User user = new User();
        user.setUsername("hello");
        //执行该方法的时候是在业务线程中执行,最终写会落到IO线程中
        //如果之后修改了对象的信息,那么得到的结果将不是当前写出去的结果
        ctx.writeAndFlush(user);
        user.setUsername("world");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
    }

    @Override
    public void channelUnregistered(final  ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        final Channel parent = ctx.channel().parent();

        ctx.close().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
               if(future.isDone()){
                   parent.close();
               }
            }
        });

    }
}
