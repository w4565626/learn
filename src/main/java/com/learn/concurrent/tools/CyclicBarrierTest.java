package com.learn.concurrent.tools;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier：栅栏回环，阻塞一组线程直到某个事件发生，并可反复使用
 * 注意：1.使用了ReentrantLock独占锁，并发可能不是很高
 * 2. 如果在等待过程中，线程中断了，就抛出异常。
 * 3.如果线程被其他的CyclicBarrier唤醒了，这个事件不能return了，继续循环阻塞。
 * 4.计数器可以使用reset方法重置，CountDownLatch不行。
 */
public class CyclicBarrierTest {
    private static int count = 3;
    private static ExecutorService pool = Executors.newCachedThreadPool();
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(count, new Runnable() {
        public void run() {
            System.out.println("初始化CyclicBarrier");
        }
    });

    public static void main(String[] args) {
        for (int i = 0; i < count; i++) {
            pool.execute(new Runnable() {
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + "开始等待其他线程");
                        cyclicBarrier.await();//
                        System.out.println(Thread.currentThread().getName() + "开始执行");
                        // 工作线程开始处理，这里用Thread.sleep()来模拟业务处理
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + "执行完毕");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
