package com.panlingxiao.concurrent;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: panlingxiao
 * @Date: 2017/1/18 0018
 * @Description:
 */
public class CopyOnWriteArrayListTest {

    public static void main(String[] args) {
        CopyOnWriteArrayList<String > copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add("1");
        copyOnWriteArrayList.add("2");
        copyOnWriteArrayList.add("3");
        copyOnWriteArrayList.add("4");
        copyOnWriteArrayList.add("5");
        new Thread(()->{
            copyOnWriteArrayList.remove("4");

        }).start();

        new Thread(()->{
            copyOnWriteArrayList.remove("5");
        }).start();

    }
}
