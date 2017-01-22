package com.panlingxiao.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;

/**
 * @Author: panlingxiao
 * @Date: 2016/12/6 0006
 * @Description:
 */
public class ByteBufAllocatorTest {

    public static void main(String[] args) {
        PooledByteBufAllocator pooledByteBufAllocator = PooledByteBufAllocator.DEFAULT;
        ByteBuf buffer = pooledByteBufAllocator.buffer(1024);
        System.out.println(buffer.refCnt());

        UnpooledByteBufAllocator unpooledByteBufAllocator = UnpooledByteBufAllocator.DEFAULT;
        ByteBuf buffer2 = unpooledByteBufAllocator.buffer(1024);
        System.out.println(buffer2.refCnt());

        System.out.println("-----------------------");
        buffer.writeInt(999);
        System.out.println(buffer.readInt());



    }
}
