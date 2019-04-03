package com.learn.concurrent.collections.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ConcurrentLinkedQueue:基于链接节点的无界线程安全的队列，非阻塞方式
 * 实现线程安全队列：
 * 1.阻塞算法，可以用一个锁（入队和出队用同一把锁）或两个锁（入队和出队用不同的锁）等方式来实现
 * 2.非阻塞算法，循环CAS方式来实现
 *
 */
public class ConcurrentLinkedQueueTest {
    public static void main(String[] args) {
        ConcurrentLinkedQueue<Integer> queue=new ConcurrentLinkedQueue<>();
        for(int i=0;i<1000;i++){
            queue.offer(i);
        }
        //判断队列是否为空不要使用size()，使用isEmpty();size()遍历队列，isEmpty()只检查头队列，性能差异大
        System.out.println(queue.size());
        System.out.println(queue.peek());
    }
}
