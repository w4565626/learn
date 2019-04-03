package com.learn.concurrent.collections.list;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * CopyOnWrite:Copy-On-Write策略实现的线程安全的Arraylist,比Vector更好，解决了迭代修改问题。
 * 实现类：{@link CopyOnWriteArrayList}，{@link CopyOnWriteArraySet}
 * <p>
 * 集合的迭代修改问题：在遍历集合的时候，如果对集合做修改，会抛出{@link ConcurrentModificationException}，多线程的时候
 * <p>
 * Copy-On-Write（写入时复制）：
 * 在计算机中就是当你想要对一块内存进行修改时，我们不在原有内存块中进行写操作，
 * 而是将内存拷贝一份，在新的内存中进行写操作，写完之后呢，就将指向原来内存指针指向新的内存，原来的内存就可以被回收掉！
 * 在CopyOnWriteArrayList中，当有元素添加，会先复制原集合，然后在新的集合中做写操作，最后将旧的数组引用指向新的集合，并回收旧集合。
 * <p>
 * 优点：
 * 1.解决了集合的迭代修改问题
 * 2.读写分离
 * 3.最终一致性
 * 4.使用另外开辟空间的思路，来解决并发冲突
 * 缺点:
 * 1.写操作，需要复制集合，额外消耗内存，旧集合很大的话，会出发young gc或者full gc
 * 2.不能实时读，复制集合，元素添加需要耗时，在写操作后，读取到的数据可能不是最新的。只能保证最终数据的一致性。
 * <p>
 * 慎用！实际场景中，无法保证集合的大小，对性能影响很大。
 */
public class CopyOnWriteTest {
    public static void main(String[] args) {
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        CopyOnWriteArraySet set = new CopyOnWriteArraySet();
        forList();
    }

    //测试集合的迭代问题
    private static void forList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        for (int i : list) {
            list.remove(i);
        }
    }
}
