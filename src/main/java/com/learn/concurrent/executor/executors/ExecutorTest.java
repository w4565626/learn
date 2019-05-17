package com.learn.concurrent.executor.executors;

import java.util.concurrent.*;

/**
 * Executor:线程池，负责管理工作线程，包括一个等待执行任务队列。
 *
 */
public class ExecutorTest {
    private static ExecutorService pool;

    static {
        //创建可缓存的线程池，线程最大数为Integer.MAX_VALUE，可能会创建很多线程，产生OOM
        pool = Executors.newCachedThreadPool();
        //创建固定数量的线程池，请求可能会堆积在队列中，耗费内存，产生OOM
        pool = Executors.newFixedThreadPool(10);
        //创建只有一个线程的线程池，请求可能会堆积在队列中，耗费内存，产生OOM
        pool = Executors.newSingleThreadExecutor();
        //创建定时线程池，支持定时和周期性任务执行，线程最大数为Integer.MAX_VALUE，可能会创建很多线程，产生OOM
        pool = Executors.newScheduledThreadPool(10);
        //ThreadPoolExecutor方式创建，推荐！
        //corePoolSize - 线程池核心池的基本大小。
        //maximumPoolSize - 线程池的最大线程数。
        //keepAliveTime - 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。线程最大空闲时间
        //unit - keepAliveTime 的时间单位。
        //workQueue - 用来储存等待执行任务的队列。
        //threadFactory - 线程工厂。
        //handler - 拒绝策略。当线程池和队列都满了时，新任务的处理策略
        pool = new ThreadPoolExecutor(0, 1000,
                60L, TimeUnit.SECONDS,
                //使用有界队列，线程池满了会抛出RejectedExecutionException
                new ArrayBlockingQueue<Runnable>(10), new MyReject());
    }

    //    ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
    //    ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
    //    ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
    //    ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
    //自定义拒绝策略
    private static class MyReject implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println(r.toString());
        }
    }

    public static void main(String[] args) {
        //局部变量创建线程池，一定要手动关闭
//        pool = Executors.newCachedThreadPool();
        pool.execute(() -> System.out.println("test"));
        pool.shutdown();
    }
}
