package com.learn.concurrent.executor;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * future模式：异步调用，不会阻塞主线程，和普通异步的区别就是可以在线程结束后获取返回值。
 * <p>
 * 实现类：
 * FutureTask：可取消的异步任务，同时继承future和runnable,
 *
 * ScheduledFuture:继承了Comparable和delayed接口，具有延迟执行，排序并获得异步结果。
 */
public class FutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        //线程池方式实现
        Future<Integer> submit = pool.submit(() -> 1);
        pool.shutdown();
        for (; ; ) {
            if (pool.isTerminated()) {
                System.out.println(submit.get());
                break;
            }
        }
    }

}
