package com.learn.concurrent.volatileTest;

import javafx.concurrent.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Volatile ：禁止指令重排序
 * 编译器在生产字节码时，在指令序列中插入内存屏障来禁止特定类型的处理器重排序。
 * lock前缀指令：
 * 1.将当前处理器的缓存行中的数据立即写入系统内存
 * 2.使其他处理器缓存中的缓存了该内存地址的数据无效。（MESI协议保证缓存一致性）
 */
public class VolatileTest {
    private static ExecutorService pool = Executors.newCachedThreadPool();
    /**
     * happens-before原则
     * 1：要求前一个操作对后一个操作可见
     * 2.前一个操作按顺序排在第二个操作之前
     * 注：不要求前一个操作一定在后一个操作之前执行，没有时间上的关系！
     * <p>
     * as-if-serial语义
     * 不管怎么重排序，单线程的执行结果不会改变。
     */
    int a = 0; //a happens-before b
    int b = 0; //b happens-before c
    /**
     * 数据依赖性不会发生重排序，c操作永远在a,b之后，a和b会发生重排序
     */
    int c = a * b; //a happens-before c

    public void set() {
        a = 1;
        b = 1;
    }

    public void loop() {
        while (b == 0) continue;
        if (a == 1) {
            System.out.println("i am here");
        } else {
            System.out.println("what is wrong");
        }
    }

    // TODO: 2019/2/14 为啥每次都是i am here
    public static void main(String[] args) {
        final VolatileTest test = new VolatileTest();
        for (int i = 0; i < 100; i++) {
            pool.execute(new Runnable() {
                public void run() {
                    test.loop();
                }
            });
            pool.execute(new Runnable() {
                public void run() {
                    test.set();
                }
            });
        }
        pool.shutdown();
    }
}
