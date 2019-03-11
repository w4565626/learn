package com.learn.collections.map;

import java.util.HashMap;

/**
 * HashMap：非线程安全
 * 1.7：采用数组+链表实现，本质是一个数组，每个元素都是一个单向链表。
 *
 * 1.8：采用数组+链表+红黑树
 * 红黑树的作用：1.7中查找通过hash找到数组下标，并顺着链表查询，实际复杂度为O{n}，
 * 1.8中当链表的元素超过8个，会转换为红黑树，降低时间复杂度为O(logN)
 *
 * 数组扩容：新的大数组替换原来的小数组，数组为原来的两倍，并将数据迁移。
 *
 */
public class HashMapTest {
    public static void main(String[] args) {
        HashMap<String,String> map=new HashMap<String, String>();
    }
}
