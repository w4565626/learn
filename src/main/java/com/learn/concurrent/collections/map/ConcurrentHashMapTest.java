package com.learn.concurrent.collections.map;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ConcurrentHashMap: 线程安全
 * 实现原理：
 * 1.7:实际上就是Segment分段数组，Segment继承ReentrantLock来加锁，只要保证每个Segment线程安全，全局的ConcurrentHashMap也就线程安全。
 * 扩容：Segment默认16，最多支持16个线程并发写，初始化后segment 数组不可扩容。
 * 扩容是 segment 数组某个位置内部的数组 HashEntry\[] 进行扩容，扩容后，容量为原来的 2 倍。
 * <p>
 * 1.8：抛弃了Segment分段数组，利用CAS+Synchronized来保证并发更新的安全，底层采用数组+链表+红黑树的存储结构。
 * 红黑树的作用：1.7中查找通过hash找到数组下标，并顺着链表查询，实际复杂度为O{n}，
 * 1.8中当链表的元素超过8个，会转换为红黑树，降低时间复杂度为O(logN)
 * <p>
 * 问题：
 * 1.并发下初始化数组，对sizeCtl做cas操作实现同步
 * 2.并发下如何扩容：通过CAS设置sizeCtl与transferIndex变量，协调多个线程对table数组中的node进行迁移。
 * <p>
 * sizeCtl:以volatile的方式读取sizeCtl属性，来判断ConcurrentHashMap当前所处的状态。通过cas设置sizeCtl属性，告知其他线程ConcurrentHashMap的状态变更。
 * <p>
 * transferIndex:扩容索引，表示已经分配给扩容线程的table数组索引位置。主要用来协调多个线程，并发安全地获取迁移任务（hash桶）
 */
public class ConcurrentHashMapTest {
    private Map<Integer, Integer> cache = new ConcurrentHashMap<>(15);

    public static void main(String[] args) {
        ConcurrentHashMapTest ch = new ConcurrentHashMapTest();
        System.out.println(ch.fibonaacci(80));
    }

    //测试ConcurrentHashMap死循环bug，原因是递归创建 ConcurrentHashMap 对象
    public int fibonaacci(Integer i) {
        if (i == 0 || i == 1) {
            return i;
        }

        return cache.computeIfAbsent(i, (key) -> {
            System.out.println("fibonaacci : " + key);
            return fibonaacci(key - 1) + fibonaacci(key - 2);
        });
    }

    private void test() {
        final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            pool.execute(new Runnable() {
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        map.put(UUID.randomUUID().toString(), "");
                    }
                }
            });
        }
        pool.shutdown();
        while (true) {
            if (pool.isTerminated()) {
                //线程安全的结果应该为10000，不安全会小于这个数字
                System.out.println(map.size());
                break;
            }
        }
    }
}
