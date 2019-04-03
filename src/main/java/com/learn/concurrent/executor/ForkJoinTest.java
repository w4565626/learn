package com.learn.concurrent.executor;

import lombok.AllArgsConstructor;

import java.util.concurrent.*;

/**
 * Fork/Join框架:可以将一个任务拆分成多个子任务，最后将子任务的结果合并。
 * <p>
 * 工作窃取算法：某个线程从其他队列窃取任务来执行。
 * <p>
 * RecursiveAction:没有返回结果的任务
 * RecursiveTask:有返回结果的任务
 * ForkJoinPool：需要使用的线程池
 */
public class ForkJoinTest {
    //应用进程间共享，推荐使用。不要new
    private static ForkJoinPool pool = ForkJoinPool.commonPool();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Task task = new Task(1, 1001, 200);
        ForkJoinTask<Integer> result = pool.submit(task);
        pool.shutdown();
        //异常捕获
        if (task.isCompletedAbnormally()) {
            task.getException().printStackTrace();
        }
        System.out.println("最终的结果：" + result.get());
    }

    @AllArgsConstructor
    private static class Task extends RecursiveTask<Integer> {
        private int start;
        private int end;
        private int threshold;

        @Override
        protected Integer compute() {

            int sum = 0;
            //判断任务是否足够小
            boolean flag = end - start <= threshold;
            //不够小，继续拆分为两个子任务
            if (!flag) {
                Task left = new Task(start, (start + end) / 2, threshold);
                Task right = new Task((start + end) / 2 + 1, end, threshold);
                //执行子任务
                left.fork();
                right.fork();
                //获取子任务执行结果
                Integer lr = left.join();
                Integer rr = right.join();
                sum = lr + rr;
            } else {
                //够小，执行子任务
                System.out.println("开始计算：start:" + start + ",end:" + end);
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
//                throw new RuntimeException("抛出了异常");
            }
            return sum;
        }
    }

}
