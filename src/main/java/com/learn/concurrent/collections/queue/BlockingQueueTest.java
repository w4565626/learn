package com.learn.concurrent.collections.queue;

import java.util.concurrent.*;

/**
 * BlockingQueue:支持当获取队列元素但是队列为空时，会阻塞等待队列中有元素再返回；
 * 也支持添加元素时，如果队列已满，那么等到队列可以放入新元素时再放入。
 * ---------------------
 * add         增加一个元索                      如果队列已满，则抛出一个IIIegaISlabEepeplian异常
 * remove   移除并返回队列头部的元素     如果队列为空，则抛出一个NoSuchElementException异常
 * element  返回队列头部的元素              如果队列为空，则抛出一个NoSuchElementException异常
 * offer       添加一个元素并返回true        如果队列已满，则返回false
 * poll         移除并返问队列头部的元素     如果队列为空，则返回null
 * peek       返回队列头部的元素              如果队列为空，则返回null
 * put         添加一个元素                       如果队列满，则阻塞
 * take        移除并返回队列头部的元素       如果队列空，则阻塞
 * ---------------------
 * ArrayBlockingQueue ：有界队列实现类，底层采用数组来实现。生产者-消费者模式常用
 * 其并发控制采用可重入锁来控制，不管是插入操作还是读取操作，都需要获取到锁才能进行操作。
 * <p>
 * LinkedBlockingQueue：底层基于单向列表实现的阻塞队列，可以当作有界或者无界队列，
 * 使用了put和take两把锁，意味着可以在队尾添加元素的同时在队头取出元素。
 * <p>
 * SynchronousQueue:同步队列，非线程并发的同步，指的是一个读线程匹配一个写线程，很少用。
 * <p>
 * PriorityBlockingQueue：无界队列，带排序，put不会阻塞，take会阻塞，用了基于数组的二叉堆来存放元素
 */
public class BlockingQueueTest {
    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(100);

        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>();

        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();

        PriorityBlockingQueue<Integer> priorityBlockingQueue=new PriorityBlockingQueue<>();
    }
}
