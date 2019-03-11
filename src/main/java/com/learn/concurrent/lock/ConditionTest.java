package com.learn.concurrent.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition：多线程间协调通信的工具类，使得某个，或者某些线程一起等待某个条件（Condition）,
 * 只有当该条件具备( signal 或者 signalAll方法被带调用)时 ，这些等待线程才会被唤醒，从而重新争夺锁
 * 替代wait()/notify()/notifyAll()
 * 实现原理：
 * 1.线程A调用ReentrantLock.lock()时，线程A加入AQS的FIFO等待队列
 * 2.线程A调用 condition.await()时，线程A从AQS队列移除，并释放锁
 * 3.接着马上加入condition的等待队列，并等待signal信号
 * 4.线程B因为A释放锁，被唤醒，并获取到锁，加入AQS的FIFO队列
 * 5.线程B调用signal()方法，此时condition的等待队列只有A一个节点，A节点被取出并放入FIFO队列，此时线程A并没有唤醒
 * 6.线程B调用ReentrantLock.unlock(),释放锁，并从AQS队列中移除，此时AQS队列中只有线程A，于是AQS释放锁后按队列顺序唤醒线程
 * 线程A被唤醒，继续执行。
 * 7.线程A释放锁，执行完毕。
 * 和wait()/notify()/notifyAll()的区别：
 * 1.wait()/notify()/notifyAll()只能用在synchronized 块内部，
 * 2.condition可以创建不同的wait集合
 * 3.condition必须由lock创建，不能new产生
 * 4.Condition支持多个等待队列，一个lock可以绑定多个Condition
 * 5.Condition支持响应中断、定时功能
 */
public class ConditionTest {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        //线程A
        pool.execute(new Runnable() {
            public void run() {
                lock.lock();
                System.out.println("我要等一个新信号:" + this);
                try {
                    //释放锁，并休眠当前线程，相当于wait（）
                    condition.await();
                    System.out.println("拿到一个信号：" + this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        //线程B
        pool.execute(new Runnable() {
            public void run() {
                lock.lock();
                System.out.println("我拿到锁了");
                try {
                    Thread.sleep(3000);
                    //唤醒所有线程（实际并没有真正唤醒，只是将线程从condition移入AQS队列），相当于notify()
                    condition.signal();
                    //相当于notifyAll()
//                    condition.signalAll();
                    System.out.println("我发了一个信号");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //释放锁，并唤醒signal（）的线程
                    lock.unlock();
                }
            }
        });
        pool.shutdown();
    }
}
