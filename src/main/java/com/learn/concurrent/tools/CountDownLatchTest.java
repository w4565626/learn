package com.learn.concurrent.tools;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**CountDownLatch：构造一个计数器，能够使一个线程等待其他线程完成工作后在执行，初始值不可变，需要可使用CyclicBarrier
 * 当前场景CountDownLatch实现准确并发，验证ArrayList非线程安全
 * 使用场景：
 * 1.实现最大得并行性，模拟高并发
 * 2.开始执行前等待n个线程完成各自任务，常用于应用程序启动类处理用户请求前，确保外部系统已经启动和运行
 * 3.死锁检测，可以使用n个线程访问共享资源，每次测试线程数目不同，并尝试尝试死锁
 */
public class CountDownLatchTest {


    //证明ArrayList非线程安全，期待结果是小于2000或者出现数组越界。
    public void add() throws InterruptedException {
         final List<Integer> data = new ArrayList<Integer>();//可以换成Vector,实现线程安全
        ExecutorService pool = Executors.newCachedThreadPool();
        final CountDownLatch count=new CountDownLatch(20);
        Runnable task= () -> {
            try {
                count.await(); //等待其他线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 100; i++) {
                data.add(i);
            }
        };
        for (int i = 0; i < 20; i++) {//偶尔出现等于2000，原因是for循环没有准确的并发。
            pool.execute(task);
            count.countDown();//计数器-1
        }
        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println(data.size());
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchTest concurrent = new CountDownLatchTest();
        //单次测试，list大小不会变动，调用调用会得到不同的结果
        for (int i = 0; i < 100; i++) {
            concurrent.add();
        }
    }
}
