package com.panlingxiao.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by panlingxiao on 2016/11/4.
 */
public class BufferTest {

    /**
     * 针对ByteBuf的某个位置写入4个字节,
     * @see ByteBuf#setIndex(int, int)
     */
    @Test
    public void testWriteInt(){
        ByteBuf buf = Unpooled.buffer();
        buf.writerIndex(4);
        buf.setInt(0, -1);
        byte[] data = new byte[4];
        buf.readBytes(data);
        System.out.println(Arrays.toString(data));
    }

    /**
     * 返回一个ByteBuf的切片内容。
     * 返回的切片内容与原始的ByteBuf分别维护者自己的指针，因此返回的内容是原始内容的一份拷贝。
     * slice操作不会修改ByteBuf的读写指针的位置，同时需要注意的是,slice操作不会调用ByteBuf的retain方法，因此ByteBuf的
     * 引用计数值不会加1。
     *
     * @see ByteBuf#slice(int, int)
     */
    @Test
    public void testSlice(){

    }
}
