package com.learn.concurrent.collections.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * List:
 */
public class listTest {
    /**
     * arraylist和linkedlist比较
     */
    @Test
    public void test() {
        List<String> al = new ArrayList<String>();
        List<String> ll = new LinkedList<String>();
        long startTime = System.currentTimeMillis();
        al.add("test");
        long endTime = System.currentTimeMillis();
        long s = endTime - startTime;
        System.out.println("写入数据到ArrayList：，用时：" + s);

        startTime = System.currentTimeMillis();
        ll.add("test");
        endTime = System.currentTimeMillis();
         s = endTime - startTime;
        System.out.println("写入数据到LinkedList：，用时：" + s);

        startTime = System.currentTimeMillis();
        String al1 = al.get(0);
        endTime = System.currentTimeMillis();
        s = endTime - startTime;
        System.out.println("读取数据到ArrayList：，用时：" + s);

        startTime = System.currentTimeMillis();
        al1 = ll.get(0);
        endTime = System.currentTimeMillis();
        s = endTime - startTime;
        System.out.println("读取数据到LinkedList：，用时：" + s);
    }
}
