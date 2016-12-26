package com.panlingxiao.netty.eventexecutor;

import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.SingleThreadEventExecutor;

import java.util.concurrent.ThreadFactory;

/**
 * @Author: panlingxiao
 * @Date: 2016/12/25 0025
 * @Description:
 */
public class SingleThreadEventExecutorTest {

    public static void main(String[] args) {
        long currentTimeNanos = System.nanoTime();
        long selectDeadLineNanos = currentTimeNanos+new MySingleThreadEventExecutor(null,new DefaultThreadFactory("a"),true).delayNanos(currentTimeNanos);
        System.out.println("currentTimeNanos:" + currentTimeNanos);
        System.out.println("selectDeadLineNanos:" + selectDeadLineNanos);
        long timeoutMillis = (selectDeadLineNanos - currentTimeNanos + 500000L) / 1000000L;
        System.out.println(timeoutMillis);
    }

    static class MySingleThreadEventExecutor extends SingleThreadEventExecutor {
        protected MySingleThreadEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
            super(parent, threadFactory, addTaskWakesUp);
        }

        @Override
        protected void run() {

        }

        @Override
        public long delayNanos(long currentTimeNanos) {
            return super.delayNanos(currentTimeNanos);
        }
    }

    ;
}
