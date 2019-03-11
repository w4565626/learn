package com.learn.concurrent.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * synchronized：同步锁，具有原子性和可见性，可重入(悲观锁)
 * 应用方式：1.修饰实例方法 2.修饰静态方法 3.修改代码块
 * 实现原理：
 * 基于进入和退出Monitor对象实现
 * 对象在内存中的布局分为3个区域：对象头，实例数据和对齐填充
 * 对象头主要由Mark Word 和 Class Metadata Address 组成
 * 其中Mark Word在默认情况下存储着对象的HashCode、分代年龄、锁标记位等
 * 锁状态：无锁、偏向锁、轻量级锁、重量级锁
 *
 * 步骤：
 * ObjectMonitor中有两个队列，_WaitSet 和 _EntryList，
 * 用来保存ObjectWaiter对象列表( 每个等待锁的线程都会被封装成ObjectWaiter对象)，
 * _owner指向持有ObjectMonitor对象的线程。
 * 当多个线程同时访问一段同步代码时，首先会进入 _EntryList 集合，
 * 当线程获取到对象的monitor 后进入 _Owner 区域并把monitor中的owner变量设置为当前线程同时monitor中的计数器count加1，
 * 若线程调用 wait() 方法，将释放当前持有的monitor，owner变量恢复为null，count自减1，
 * 同时该线程进入 WaitSe t集合中等待被唤醒。若当前线程执行完毕也将释放monitor(锁)并复位变量的值，以便其他线程进入获取monitor(锁)
 *
 * 缺点：
 * 1.无法中断
 * 2.notify/notifyAll和wait方法必须处于synchronized中
 * 3.无法通过投票得到锁
 * 4.同步还要求锁的释放只能在与获得锁所在的堆栈帧相同的堆栈帧中进行
 */
public class SyncTest {
    private int a = 0;

    //申明之后具有原子性和可见性
    public synchronized void write() {
        a++;
    }

    public synchronized void read() {
        int i = a;
        System.out.println(i);
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        final SyncTest test = new SyncTest();
        for (int i = 0; i < 10000; i++) {
            pool.execute(new Runnable() {
                public void run() {
                    test.write();
                }
            });
            pool.execute(new Runnable() {
                public void run() {
                    test.write();
                }
            });
        }
        pool.shutdown();
    }
}
