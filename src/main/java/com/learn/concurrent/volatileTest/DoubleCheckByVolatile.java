package com.learn.concurrent.volatileTest;

import org.junit.Test;

/**
 * 单例模式-双重检查-基于volatile禁止重排序方案
 * 优点：可以对静态字段和实例字段都实现延迟初始化
 * 缺点：代码量比较大
 */
public class DoubleCheckByVolatile {
    //一定要加volatile
    private static volatile DoubleCheckByVolatile Instance;

    //多线程模式下，单例需要加锁
    public static DoubleCheckByVolatile getInstance() {
        //当Instance为空，才加锁，减少锁造成的执行效率问题
        if (Instance == null) {
            synchronized (DoubleCheckByVolatile.class) {
                //当Instance为空，初始化
                if (Instance == null) {
                    //1：分配对象的内存空间
                    //2:初始化对象
                    //3.设置Instance指向刚分配的内存地址
                    //问题：2和3可能会重排序
                    //解决：volatile禁止了2和3的重排序
                    Instance = new DoubleCheckByVolatile();
                }
            }
        }
        return Instance;
    }

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            DoubleCheckByVolatile instance = DoubleCheckByVolatile.getInstance();
            System.out.println(instance);
        }
    }
}
