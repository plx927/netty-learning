package com.panlingxiao.concurrent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: panlingxiao
 * @Date: 2017/1/19 0019
 * @Description:
 */
public class ConcurrentHashMapTest {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        new Thread(()->{
            concurrentHashMap.put("abc","a");
        }).start();
        new Thread(()->{
            concurrentHashMap.put("ddd","b");
        }).start();

    }
}
