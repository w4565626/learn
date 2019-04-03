package com.learn.concurrent.collections.map;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * HashMap：非线程安全，在涉及到多线程并发的情况，进行get操作有可能会引起死循环，导致CPU利用率接近100%。
 * 1.7：采用数组+链表实现，本质是一个数组，每个元素都是一个单向链表。
 * <p>
 * 1.8：采用数组+链表+红黑树
 * 红黑树的作用：1.7中查找通过hash找到数组下标，并顺着链表查询，实际复杂度为O{n}，
 * 1.8中当链表的元素超过8个，会转换为红黑树，降低时间复杂度为O(logN)
 * <p>
 * 数组扩容：新的大数组替换原来的小数组，数组为原来的两倍，并将数据迁移。
 */
public class HashMapTest {

    public static void main(String[] args) {
        final Map<String, String> map = new HashMap<String, String>();
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            pool.execute(new Runnable() {
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        map.put(UUID.randomUUID().toString(), "");
                    }
//                    System.out.println(map.size());
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
