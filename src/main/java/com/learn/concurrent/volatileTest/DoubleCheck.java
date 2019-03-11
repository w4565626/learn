package com.learn.concurrent.volatileTest;

/**
 * 基于类初始化的单例模式解决方案
 * JVM在类的初始化阶段（class被加载后，且被线程使用之前）会执行类的初始化。
 * 期间，jvm会获取到初始化锁，这个锁可以同步多个线程对同一个类的初始化。每个线程至少获取一次锁来确保这个类已经被初始化过了
 * 缺点：只能对静态字段实现延迟初始化
 * 优点：代码简洁
 *
 */
public class DoubleCheck {

    private static class InstanceHolder {
        //1：分配对象的内存空间
        //2:初始化对象
        //3.设置Instance指向刚分配的内存地址
        //问题：2和3可能会重排序
        //解决：类的初始化允许2和3重排序，但是由于锁的存在，这个重排序其他非构造线程不可见。
        private static DoubleCheck instance = new DoubleCheck();
    }

    public static DoubleCheck getInstance() {
        return InstanceHolder.instance;
    }

    public static void main(String[] args) {
        DoubleCheck.getInstance();
    }

}
