package com.learn.concurrent.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore：控制同时访问特定资源的线程数量。
 * 1.流量控制，特别是公共资源有限的应用场景，如数据库连接
 */
public class SemaphoreTest {
    private static final int count = 30;
    private static ExecutorService pool = Executors.newFixedThreadPool(count);
    private static Semaphore semaphore = new Semaphore(10);

    public static void main(String[] args) {
        for (int i = 0; i < count; i++) {
            pool.execute(new Runnable() {
                public void run() {
                    try {
                        //获取许可证，共享方式获取资源
                        semaphore.acquire();
                        System.out.println("save data");
//                        System.out.println(semaphore.getQueueLength());
                        //释放许可证
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        pool.shutdown();
    }
}
