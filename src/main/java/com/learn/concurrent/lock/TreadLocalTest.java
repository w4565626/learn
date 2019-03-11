package com.learn.concurrent.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal: 线程变量，以ThreadLocal对象为key,任意对象为value的线程绑定的存储方式
 * <p>
 * ThreadLocalMap说明：是ThreadLocal的内部map实现。没有实现Map接口，独立实现。
 * hash冲突解决：
 * 开放地址法，实例实例{@link ThreadLocal}：根据初始key的hashcode值确定元素在table数组中的位置,
 * 如果发现这个位置上已经有其他key值的元素被占用，则利用固定的算法寻找一定步长的下个位置，依次判断，直至找到能够存放的位置。
 * 链地址法，实例{@link java.util.HashMap}：哈希地址相同的记录存放在同一个链表里面，查找、插入和删除主要在此链表中进行
 *
 * 内存泄露：
 * 由于ThreadLocalMap的key是弱引用，而Value是强引用。这就导致了一个问题，
 * ThreadLocal在没有外部对象强引用时，发生GC时弱引用Key会被回收，而Value不会回收，
 * 如果创建ThreadLocal的线程一直持续运行，那么这个Entry对象中的value就有可能一直得不到回收，发生内存泄露。
 * 解决：ThreadLocal最后必须调用remove方法。
 *
 * 应用场景：
 * 1.为每个线程创建独立的数据库连接
 */
public class TreadLocalTest {
   static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 100;
        }
    };

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        Runnable runnable = new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    threadLocal.set(threadLocal.get() + 10);
                    System.out.println(Thread.currentThread()+":"+threadLocal.get() );
                }
                //必须调用！避免内存泄露
                threadLocal.remove();
            }
        };
        pool.execute(runnable);
        pool.execute(runnable);
        pool.shutdown();
    }
}
