package com.learn.concurrent.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;


/**
 * ReentrantLock : 可重入锁，替换synchronized使用，但是更加强大
 * 使用了AQS的独占功能,分为公平锁和非公平锁
 * 公平锁实现：线程获取锁的顺序和调用lock的顺序一样，FIFO
 * 继承AQS的CLH队列
 *
 * 和synchronized的区别：
 * 1.synchronized只能是非公平锁
 * 2.ReentrantLock提供了一个Condition（条件）类，用来实现分组唤醒需要唤醒的线程们，synchronized要么随机唤醒一个线程要么唤醒全部线程
 * 3.中断、超时等待锁,lock.lockInterruptibly()来实现
 */
public class ReentrantLockTest extends Thread {
    private static int a = 0;
    private  static ReentrantLock lock = new ReentrantLock();
    private static ExecutorService pool = Executors.newCachedThreadPool();

    public void run() {
        for (int i = 0; i < 10000000; i++) {
//            a++; //不加lock
            lock.lock();//加锁，拿不到锁就一直阻塞，内存语义：相当于volatile的读
            lock.tryLock(); //马上返回true或者false,可加超时时间
            try {
                a++;
            } finally {
                //必须在finally中释放锁，内存语义：相当于volatile的写
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockTest test1 = new ReentrantLockTest();
        ReentrantLockTest test2 = new ReentrantLockTest();
        pool.execute(test1);
        pool.execute(test2);
        pool.shutdown();
        while (true){
            if (pool.isTerminated()) {
                //不加lock，a为小于20000000的不确定数
                //加了lock，a=20000000
                System.out.println(a);
                break;
            }
        }
    }
}
