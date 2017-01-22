package com.panlingxiao.concurrent;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Author: panlingxiao
 * @Date: 2017/1/19 0019
 * @Description:
 */
public class AtomicIntegerArrayTest {

    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);
        atomicIntegerArray.set(0,10);
    }
}
