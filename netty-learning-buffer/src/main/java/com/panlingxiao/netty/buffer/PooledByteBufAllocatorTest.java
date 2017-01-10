package com.panlingxiao.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: panlingxiao
 * @Date: 2017/1/10 0010
 * @Description:
 */
public class PooledByteBufAllocatorTest {

    private static final Logger log = LoggerFactory.getLogger(PooledByteBufAllocatorTest.class);

    public static void main(String[] args) {
        /**
         * (1).
         * 底层在创建的时候会根据执行平台去选择Direct Buffer还是Heap Buffer.
         * 只有底层提供了可靠的低级别ByteBuffer访问API并且指定-Dio.netty.noPreferDirect选项
         * 默认情况下选择的是DirectByteBuffer
         *
         * PoolByteBuffAllocator底层维护着一个PAGE_SIZE和MAX_ORDER
         * 1.DEFAULT_PAGE_SIZE:PAGE_SIZE的大小必须为2的幂次方并且必须大于2^12(4096)
         * 2.MAX_ORDER:通过PAGE_SIZE和MAX_ORDER计算出CHUNK_SIZE
         *
         * defaultChunkSize = DEFAULT_PAGE_SIZE >> MAX_ORDER
         * 3.DEFAULT_NUM_HEAP_ARENA / DEFAULT_NUM_DIRECT_ARENA
         * 会基于CPU核数以及maxMemory()/defaultChunkSize/2/3来进行比较判断
         * 默认情况下:DEFAULT_NUM_HEAP_ARENA与DEFAULT_NUM_DIRECT_ARENA的值为8
         *
         * 4.缓存的大小
         *  DEFAULT_TINY_CACHE_SIZE: 512
         *  DEFAULT_SMALL_CACHE_SIZE: 256
         *  DEFAULT_NORMAL_CACHE_SIZE: 64
         *
         * 5.DEFAULT_MAX_CACHED_BUFFER_CAPACITY:默认情况下缓存Buffer的最大容量(32k)
         *
         * 6.DEFAULT_CACHE_TRIM_INTERVAL:当缓存的Entry没有被频繁使用时,缓存的Entry应该被回收，被回收的最大间隔。
         *
         * (2).
         *  PoolByteBufAllocator底层维护了两个PoolArena
         *  PoolArena<byte[]>[] heapArenas
         *  PoolArena<ByteBuffer>[] directArenas
         *  在创建PoolByteBufAllocator会分别创建这两个PoolArena数组
         *  数组的大小为DEFAULT_NUM_HEAP_ARENA和DEFAULT_NUM_DIRECT_ARENA,其个数都为8.
         *
         *  每个PoolArena底层都维护两个PoolSubpage<T>数组:
         *  分别是tinySubpagePools和smallSubpagePools
         *  如是是DirectArena,则PoolSubpage<ByteBuffer>,如果是HeapAreana,则PoolSubpage<byte[]>
         *  tinySubpagePools的个数固定为512>>>4-->(32)，而smallSubpagePools的个数为pageShifts-9
         *
         *
         *
         *
         *
         *
         */
        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;

        Runtime runtime = Runtime.getRuntime();
        log.info("maxMemory:{}", runtime.maxMemory());


        int pageSize = 8192;
        int pageShifts =  Integer.SIZE - 1 - Integer.numberOfLeadingZeros(pageSize);
        log.info("pageShifts:{}",pageShifts);

        int maxOrder = 11;
        int chunkSize = pageSize << maxOrder;
        log.info("chunkSize:{}",chunkSize);

        ByteBuf buffer = allocator.buffer();
        log.info("buffer:{}",buffer);


        //PoolArean底层创建PoolSubpagePools的大小
        int numTinySubpagePools = 512 >>>4;
        log.info("numTinySubpagePools:{}",numTinySubpagePools);
    }
}
