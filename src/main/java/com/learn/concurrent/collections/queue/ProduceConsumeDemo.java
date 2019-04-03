package com.learn.concurrent.collections.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//使用ArrayBlockingQueue实现生产者-消费者模式
public class ProduceConsumeDemo {
    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        //消费者<生产者，不会阻塞
        for (int i = 0; i < 11; i++) {
            pool.execute(new Produce(queue, "测试"));
        }
//        pool.execute(new Produce(queue, "测试"));
        //消费者>生产者，一直阻塞
        for (int i = 0; i < 10; i++) {
            pool.execute(new comsume(queue));
        }
        pool.shutdown();
        while (true) {
            if (pool.isTerminated()) {
                System.out.println("线程池关闭");
                break;
            }
        }
    }

    private static class Produce implements Runnable {
        private String msg;
        private ArrayBlockingQueue<String> queue;

        public Produce(ArrayBlockingQueue<String> queue, String msg) {
            this.msg = msg;
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                queue.put(msg);
                System.out.println("生产一条消息:" + msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class comsume implements Runnable {
        private ArrayBlockingQueue<String> queue;

        public comsume(ArrayBlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                String take = queue.take();
                System.out.println("消费一条消息：" + take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
