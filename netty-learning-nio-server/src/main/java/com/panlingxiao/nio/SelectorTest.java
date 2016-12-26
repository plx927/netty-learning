package com.panlingxiao.nio;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.concurrent.TimeUnit;

/**
 * @Author: panlingxiao
 * @Date: 2016/12/25 0025
 * @Description:
 */
public class SelectorTest {


    public static void main(String[] args) throws IOException{
        final Selector selector = Selector.open();
        new Thread(){
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    //当selector阻塞一个线程时，另外一个线程执行wakeup，那么会唤醒会select立即返回
                    selector.wakeup();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //selector.select
        selector.select(100000000);
    }
}
