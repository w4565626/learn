package com.learn.concurrent.collections.map;

import java.util.Hashtable;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 过时的类，其实就是线程安全的hashmap
 * 实现原理：在put,get等操作使用synchronized来保证线程安全
 * 过时原因：synchronized是悲观锁，当线程1put的时候，其他线程不能做put/get等操作，进入阻塞或者轮询状态
 *
 */
public class HashTableTest {

    public static void main(String[] args) {
        final Hashtable<String,String> map=new Hashtable<String, String>();
        ExecutorService pool= Executors.newCachedThreadPool();
        for(int i=0;i<100;i++){
            pool.execute(new Runnable() {
                public void run() {
                    for(int i=0;i<100;i++){
                        map.put(UUID.randomUUID().toString(),"");
                    }
                }
            });
        }
        pool.shutdown();
        while (true){
            if (pool.isTerminated()){
                //线程安全的结果应该为10000，不安全会小于这个数字
                System.out.println(map.size());
                break;
            }
        }
    }

}
