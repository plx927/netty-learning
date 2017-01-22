package com.panlingxiao.netty.buffer.pool;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

/**
 * @Author: panlingxiao
 * @Date: 2017/1/15 0015
 * @Description:
 */
public class PooledByteBufTest {

    public static void main(String[] args) {
        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
        ByteBuf buffer = allocator.buffer();
        ByteBuf buffer2 = allocator.buffer();
        System.out.println(buffer);
        System.out.println(buffer2);
    }
}
