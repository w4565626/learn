package com.learn.concurrent.collections.dequeue;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * dequeue(double ended queue):双向队列,，支持两端的元素插入和移除。既支持先进先出队列又支持先进后出栈
 * 实现包括：
 * LinkedList ：大小可变的链表双端队列，允许元素为插入null，线程不安全
 * ArrayDeque 大下可变的数组双端队列，不允许插入null，线程不安全
 * ConcurrentLinkedDeque 大小可变且线程安全的链表双端队列，非阻塞，不允许插入null。
 * LinkedBlockingDeque 为线程安全的双端队列，在队列为空的情况下，获取操作将会阻塞，直到有元素添加。
 */
public class dequeueTest {
    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        //测试线程是否安全，主要是判断写操作是否独占，多线程做写操作判断大小测试即可。
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            pool.execute(() -> {
                //入栈
                stack.push(finalI);
//            System.out.println("入栈完成，大小：" + stack.size());
            });
        }
//        pool.execute(() -> {
//            for (int i = 0; i < 100; i++) {
//                if (!stack.isEmpty()) {
//                    //出栈
//                    Integer pop = stack.pop();
//                    System.out.println(pop);
//                }
//            }
//        });
        pool.shutdown();
        for(;;){
            if(pool.isTerminated()){
                //如果线程安全，大小应该为100
                System.out.println(stack.size());
                break;
            }
        }
    }
}
