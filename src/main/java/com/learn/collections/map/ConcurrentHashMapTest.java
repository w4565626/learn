package com.learn.collections.map;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap: 线程安全
 * 实现原理：
 * 1.7:实际上就是Segment分段数组，Segment继承ReentrantLock来加锁，只要保证每个Segment线程安全，全局的ConcurrentHashMap也就线程安全。
 * 扩容：Segment默认16，最多支持16个线程并发写，初始化后segment 数组不可扩容。
 * 扩容是 segment 数组某个位置内部的数组 HashEntry\[] 进行扩容，扩容后，容量为原来的 2 倍。
 *
 * 1.8：
 */
public class ConcurrentHashMapTest {
    private static ConcurrentHashMap<String,String> map=new ConcurrentHashMap<String, String>();
}
