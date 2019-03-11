package com.learn.concurrent.tools;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 并发基础：AQS，是{@link AbstractQueuedSynchronizer}的简称，共享式队列同步器
 * 维护了一个单独的原子变量state表示当前同步状态(多少线程持有锁资源)和一个CLH双向队列（多线程争用资源阻塞时会进入此队列），
 * 提供了实现阻塞锁和一系列依赖FIFO等待队列的同步器的框架,许多同步器依赖此类，如
 * {@link CountDownLatch}{@link java.util.concurrent.CyclicBarrier}{@link java.util.concurrent.Semaphore}
 *  AQS定义两种资源共享方式：Exclusive（独占，只有一个线程能执行，如ReentrantLock）和Share（共享，多个线程可同时执行，如Semaphore/CountDownLatch）。
 * AQS会在资源释放后，依次唤醒队列中的所有节点，使等待线程恢复执行，直到队列为空
 * 1.state在独占锁里面一般是0或者1（重入锁代表重入次数），共享锁代表持有锁的数量
 */
public class AQSTest extends AbstractQueuedSynchronizer {
    public static void main(String[] args) {
        AQSTest aqs = new AQSTest(1);
        //独占方式获取资源
        aqs.acquire(1);
        //独占方式释放指定量的资源，state=0即为完全释放
        aqs.release(1);
    }

    public AQSTest(int state) {
        setState(state);
    }

    //访问原子变量
    public int getCount() {
        return getState();
    }

    //独占方式
    private class Exclusive extends AbstractQueuedSynchronizer {
        //尝试获取资源
        @Override
        protected boolean tryAcquire(int arg) {
            return getCount() == 0;

        }

        //尝试释放资源
        @Override
        protected boolean tryRelease(int arg) {
            return super.tryRelease(arg);

        }
    }

    //共享方式，模拟CountDownLatch实现
    private class share extends AbstractQueuedSynchronizer {
        //尝试获取资源，负数失败，0表示成功，但无资源，正数表示成功并有资源。
        @Override
        protected int tryAcquireShared(int arg) {
            return getCount() == 0 ? 1 : -1;
        }

        //尝试释放资源
        @Override
        protected boolean tryReleaseShared(int arg) {
            while (true) {
                int count = getCount();
                if (count == 0) {
                    return false;
                }
                int next = count - 1;
                if (compareAndSetState(next, count)) {
                    return next == 0;
                }
            }
        }
    }

}
