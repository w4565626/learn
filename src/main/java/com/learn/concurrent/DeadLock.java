package com.learn.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 产生死锁
 * 四个条件：
 * 1.互斥，任意时刻一个资源只会被一个线程使用
 * 2.至少有一个任务持有一个资源，并且在等待另一个被别的任务占用的资源
 * 3.资源不会被任务抢占，意思就是一个任务持有的资源不能被别的任务抢占
 * 4.循环等待，一个任务必须等待另一个任务的资源，另一个任务正好也在等着这个任务的资源
 * 检查死锁工具:jstack pid
 */
public class DeadLock {
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private static Object A = new Object();
    private static Object B = new Object();
    private boolean flag;//让线程执行不同代码

//    public DeadLock(boolean flag) {
//        this.flag = flag;
//    }

    public void build() {

        executorService.execute(new Runnable() {
            public void run() {
                String name = Thread.currentThread().getName();
                synchronized (A) { //获取A
                    System.out.println(name + " got lockA,  want LockB");
                    try {
                        Thread.sleep(1000); //休眠1秒，这一秒A被占用
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (B) {//尝试获取B
                        System.out.println("get lock B");
                    }
                }
            }
        });
        executorService.execute(new Runnable() {
            public void run() {
                String name = Thread.currentThread().getName();
                synchronized (B) { //获取B
                    System.out.println(name + " got lockB,  want LockA");
                    try {
                        Thread.sleep(1000);//休眠1秒，这一秒B被占用
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (A) {//尝试获取A
                        System.out.println("get LockA");
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        DeadLock lockA = new DeadLock();
//        DeadLock lockB = new DeadLock(false);
        lockA.build();
//        lockB.build();
    }
}
