package com.learn.mq.rocketMQ;


import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Producer {
    private static final String url = "192.168.11.242:9876";

    /**
     * 普通发送消息
     *
     * @param msg
     * @param topic
     */
    public void send(String msg, String topic) {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        try {
            ////设置NameServer地址,此处应改为实际NameServer地址，多个地址之间用；分隔
            producer.setNamesrvAddr(url);
            //为避免程序启动的时候报错，添加此代码，可以让rocketMq自动创建topickey
            producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");
            producer.start();
            Message message = new Message(topic, msg.getBytes());
            SendResult sendResult = producer.send(message);
            System.out.println("发送的消息ID:" + sendResult.getMsgId() + "--- 发送消息的状态：" + sendResult.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }

    //发送事务消息
    public void sendTransaction(String msg, String topic) {
        TransactionMQProducer producer = new TransactionMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr(url);
        producer.setTransactionListener(new TransactionListenerImpl());
        //建立线程池
        ExecutorService pool = new ThreadPoolExecutor(2, 5, 100,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(200), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });
        producer.setExecutorService(pool);
        try {
            producer.start();
            for (int i = 0; i < 10; i++) {
                Message message = new Message(topic, msg.getBytes());
                TransactionSendResult sendResult = producer.sendMessageInTransaction(message, null);
                System.out.println("发送的消息ID:" + sendResult.getMsgId() + "--- 发送消息的状态：" + sendResult.getSendStatus());
                Thread.sleep(10);
            }
        } catch (MQClientException | InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 100000; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        producer.shutdown();
    }

    //单向消息
    public static void sendOneWay(String msg, String topic) {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        try {
            ////设置NameServer地址,此处应改为实际NameServer地址，多个地址之间用；分隔
            producer.setNamesrvAddr(url);
            //为避免程序启动的时候报错，添加此代码，可以让rocketMq自动创建topickey
            producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");
            producer.start();
            Message message = new Message(topic, msg.getBytes());
            producer.sendOneway(message);
            System.out.println("消息发送成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }

    /**
     * 选择队列发送消息,顺序消息
     *
     * @param topic
     */
    public static void send(String topic) {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        List<OrderDemo> orders = buildOrders();
        try {
            ////设置NameServer地址,此处应改为实际NameServer地址，多个地址之间用；分隔
            producer.setNamesrvAddr(url);
            //为避免程序启动的时候报错，添加此代码，可以让rocketMq自动创建topickey
            producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");
            producer.start();
            for (int i = 0; i < 10; i++) {
                String body = orders.get(i).toString();
                Message message = new Message(topic, body.getBytes());
                SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                        long id = (long) o;
                        long index = id % list.size();
                        return list.get((int) index);
                    }
                }, orders.get(i).getOrderId());
                System.out.println("发送的消息ID:" + sendResult.getMsgId() + "--- 发送消息的状态：" + sendResult.getSendStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }

    private static List<OrderDemo> buildOrders() {
        List<OrderDemo> orderList = new ArrayList<OrderDemo>();

        OrderDemo orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("推送");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        return orderList;
    }
}