package com.learn.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟内存溢出：
 * 寻找内存溢出步骤：https://www.jianshu.com/p/3479f043bc68
 *
 */
public class MemErrorTest {
    public static void main(String[] args) {
        try {
            List<Object> list = new ArrayList<>();
            for (; ; ) {
                list.add(new Object());//创建对象速度可能高于jvm回收速度
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
//        try {
//            hi();//递归造成StackOverflowError 这边因为每运行一个方法将创建一个栈帧，栈帧创建太多无法继续申请到内存扩展
//        } catch (StackOverflowError e) {
//            e.printStackTrace();
//        }
    }

    private static void hi() {
        hi();
    }

}
