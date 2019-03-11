package com.learn.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 验证volatile能否保证保证++，--操作的原子性
 * 结论：并不能
 * 解决方式：使用AtomicInteger
 */
public class AtomicTest {
    //方案2，int改为AtomicInteger，使用cas来保证原子性
//    缺点：
//    循环时间长开销很大。
//    只能保证一个共享变量的原子操作。
//    ABA问题。解决：AtomicStampedReference添加版本号
//    private static volatile AtomicInteger race = new AtomicInteger(0);
    private static volatile int race;

    //方案1，添加synchronized，缺点每次自增加锁，影响性能，优点，可以保证多个共享变量的原子操作。
    private synchronized static void insert() {
//        race.getAndIncrement();
        race++;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            test();
        }
    }

    public static void test() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //执行20次线程
        for (int i = 0; i < 20; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        insert();
                    }
                }
            });
        }
        //线程停止
        executorService.shutdown();
        while (true) {
            //判断所有线程是否已停止
            if (executorService.isTerminated()) {
                System.out.println(race);
                break;
            }
        }
    }
}
