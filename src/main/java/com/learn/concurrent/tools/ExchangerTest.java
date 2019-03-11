package com.learn.concurrent.tools;

import java.util.concurrent.*;

/**
 * Exchanger: 线程间交换数据。提供一个同步点，在这个同步点，两个线程可以交换彼此的数据。
 * 应用场景
 * 1. 遗传算法： 选出两个人作为交配对象，这时候交换两人的数据，并使用交叉规则得出两人的交配结果。
 * 2. 校对工作：比如需要纸质银行流水通过人工录入为电子银行流水，AB岗两人录入，最后校对是否录入一致。
 */
public class ExchangerTest {
    private static final Exchanger<String> EXCHANGER = new Exchanger<String>();

    private static ExecutorService pool = Executors.newFixedThreadPool(20);

    public static void main(String[] args) {
        pool.execute(new Runnable() {
            public void run() {
                String A = "银行流水A";
                try {
                    //执行exchange
                    EXCHANGER.exchange(A);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        pool.execute(new Runnable() {
            public void run() {
                String B = "银行流水B";
                try {
                    //执行exchange，交换数据获取到A
                    String A = EXCHANGER.exchange(B,10, TimeUnit.SECONDS);//有一个没有执行exchange，需要设置超时时间
                    System.out.println("A录入的数据：" + A);
                    System.out.println("B录入的数据：" + B);
                    System.out.println("A和B数据是否一致？" + A.equals(B));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        });
        pool.shutdown();
    }
}
