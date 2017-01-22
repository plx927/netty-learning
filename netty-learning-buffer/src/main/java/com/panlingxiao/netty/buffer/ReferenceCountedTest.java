package com.panlingxiao.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author: panlingxiao
 * @Date: 2017/1/16 0016
 * @Description: 参考---https://github.com/netty/netty/wiki/Reference-counted-objects
 */
public class ReferenceCountedTest {

    static final Logger log = LoggerFactory.getLogger(ReferenceCountedTest.class);

    public static void main(String[] args) {
        /**
         * 初始化引用计数对象的引用值为1
         */
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer();
        assert buf.refCnt() == 1;
        log.info("ref:{}", buf.refCnt());


        //当释放一个引用计数对象,它的引用计数值会被减1
        //当引用计数值为0时,那么它会被回收重新进入到对象池中
        boolean destroy = buf.release();
        log.info("destroyed:{}", destroy);


        //如果当引用计数值为0时，尝试去访问对象时,会引发IllegalReferenceCountException
        try {
            buf.readShort();
        } catch (Exception e) {
            log.error("引用计数值为0,访问失败:", e);
        }

        buf = PooledByteBufAllocator.DEFAULT.directBuffer();
        log.info("refCnt:{}", buf.refCnt());

        //使用retain会添加引用计数的次数
        buf.retain();
        log.info("refCnt:{}", buf.refCnt());

        boolean release = ReferenceCountUtil.release(buf);
        log.info("release:{},refcnt:{}", release, buf.refCnt());


        /**
         *  谁应用去销毁引用计数对象?
         *  谁最后访问引用计数对象，那么它就负责销毁它。
         */

        ByteBuf buf2 = PooledByteBufAllocator.DEFAULT.buffer();
        c(b(a(buf2)));
        log.info("refcnt:{}",buf2.refCnt());



        //Derived ByteBuf
        ByteBuf buf3 = PooledByteBufAllocator.DEFAULT.directBuffer();
        //衍生的ByteBuf与原来的ByteBuf共享同一块内存区域,并且其没有自己的引用计数值
        ByteBuf duplicate = buf3.duplicate();
        System.out.println("buf3's refcnt:"+buf3.refCnt());
        System.out.println("copy's refcnt:"+duplicate.refCnt());

        buf3.release();
        System.out.println("buf3's refcnt:"+buf3.refCnt());
        System.out.println("copy's refcnt:"+duplicate.refCnt());

        //与之相反的，copy产生的ByteBuf则不是一个Derived ByteBuf。



        ByteBuf buf1 = Unpooled.buffer();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }

    public static ByteBuf a(ByteBuf input) {
        input.writeByte(42);
        return input;
    }

    public static ByteBuf b(ByteBuf input) {
        try {
            ByteBuf output = input.alloc().directBuffer(input.readableBytes() + 1);
            output.writeBytes(input);
            output.writeByte(42);
            return output;
        } finally {
            input.release();
        }
    }

    public static void c(ByteBuf input) {
        System.out.println(input);
        input.release();
    }


}
