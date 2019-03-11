package com.learn.collections.map;

import java.util.HashMap;
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
        final HashMap<String,String> map=new HashMap<String, String>();
        ExecutorService pool= Executors.newCachedThreadPool();
        pool.execute(new Runnable() {
            public void run() {
                for(int i=0;i<100;i++){
                    map.put(UUID.randomUUID().toString(),"");
                }
            }
        });
        pool.shutdown();
        while (true){
            if (pool.isTerminated()){
                System.out.println(map.size());
                break;
            }
        }
    }

}
