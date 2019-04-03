package com.learn.concurrent.collections.queue;


import java.util.concurrent.LinkedTransferQueue;


/**
 * LinkedTransferQueue:基于链表实现的无界阻塞队列
 *
 * 实现原理：预占模式,意思就是消费者线程取元素时，如果队列为空，那就生成一个节点（节点元素为null）入队，然后消费者线程被等待在这个节点上，
 * 后面生产者线程入队时发现有一个元素为null的节点，生产者线程就不入队了，直接就将元素填充到该节点，唤醒该节点等待的线程，
 * 被唤醒的消费者线程取走元素，从调用的方法返回。即找到匹配的节点不入队，找不到根据how参数入队。
 */
public class LinkedTransferQueueTest {
    public static void main(String[] args) {
        LinkedTransferQueue queue=new LinkedTransferQueue();
    }
}
