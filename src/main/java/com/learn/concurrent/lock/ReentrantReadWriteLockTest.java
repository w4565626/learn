package com.learn.concurrent.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock:读写锁，一个资源能够被多个读线程访问，或者被一个写线程访问，
 * 但是不能同时存在读写线程。也就是说读写锁使用的场合是一个共享资源被大量读取操作，而只有少量的写操作（修改数据）
 * 特性：
 * 1.公平性：一样有ReentrantLock的公平锁和非公平锁
 * 2.重入性：写线程可以获取写入锁可以再次获取读取锁，读线程只能获取读取锁
 * 3.条件变量：写入锁可以使用Condition，读取锁不能使用Condition，得到UnsupportedOperationException异常
 * 4.锁降级：写入锁可以释放并获取读取锁，实现写入锁降级为读取锁
 * <p>
 * 和ReentrantLock区别：
 * 1.ReentrantLock是独占，一次只允许一个线程获取锁，ReentrantReadWriteLock的写入锁是独占，但是读取锁是共享。
 * 2.ReentrantReadWriteLock继承的ReadWriteLock并非lock类的继承
 * <p>
 * state说明：AQS的state（int 32位）一分为二，高16位表示共享锁（读取锁），低16位表示独占锁（写入锁）
 * 最大数2^16-1=65536，所以共享锁和独占锁最大数都为65535
 * <p>
 * HoldCounter说明：当前线程持有共享锁（读取锁）的数量，包括重入的数量。根据偏向锁可知，锁总是有一个线程多次获取。
 */
public class ReentrantReadWriteLockTest {
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    private static Map<String, String> map = new HashMap<String, String>();

    //模拟redis的写
    private static void set(String key, String value) {
        writeLock.lock();
        try {
            map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    //模拟redis的读
    private static String get(String key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantReadWriteLockTest.set("1", "2");
        System.out.println(ReentrantReadWriteLockTest.get("1"));
    }
}
