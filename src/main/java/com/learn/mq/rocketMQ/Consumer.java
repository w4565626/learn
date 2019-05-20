package com.learn.mq.rocketMQ;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * 消费者
 */
public class Consumer {
    private static final String url = "192.168.11.242:9876";

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.receive("TopicTest");
    }

    //普通消息消费
    public void receive(String topic) {
        //默认集群模式，同一个consumerGroup的consumer每人消费一部分，各自收到的消息不一样，最后拼接成一个完整消息
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_4");
        consumer.setNamesrvAddr(url);
        String msg = null;
        try {
            //设置消费者端消息拉取策略，表示从哪里开始消费。第一次启动有效
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //设置消费者拉取消息的策略，*表示消费该topic下的所有消息，也可以指定tag进行消息过滤
            consumer.subscribe(topic, "*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    print(list);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
            System.out.println("Consumer Started....");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    //顺序消息消费
    public void receiveOrder(String topic) {
        //默认集群模式，同一个consumerGroup的consumer每人消费一部分，各自收到的消息不一样，最后拼接成一个完整消息
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_4");
        consumer.setNamesrvAddr(url);
        String msg = null;
        try {
            //设置消费者端消息拉取策略，表示从哪里开始消费。第一次启动有效
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //设置消费者拉取消息的策略，*表示消费该topic下的所有消息，也可以指定tag进行消息过滤
            consumer.subscribe(topic, "*");
            consumer.registerMessageListener(new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeConcurrentlyContext) {
                    print(list);
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
            consumer.start();
            System.out.println("Consumer Started....");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    //广播模式消费
    public void receiveBroadcast(String topic) {
        //默认集群模式，同一个consumerGroup的consumer每人消费一部分，各自收到的消息不一样
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_4");
        consumer.setNamesrvAddr(url);
        String msg = null;
        try {
            //设置消费者端消息拉取策略，表示从哪里开始消费。第一次启动有效
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //设置消费者拉取消息的策略，*表示消费该topic下的所有消息，也可以指定tag进行消息过滤
            consumer.subscribe(topic, "*");
            //广播模式：每个消费者都能获取到完整消息
            consumer.setMessageModel(MessageModel.BROADCASTING);
            consumer.registerMessageListener(new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeConcurrentlyContext) {
                    print(list);
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
            consumer.start();
            System.out.println("Consumer Started....");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }


    private void print(List<MessageExt> list) {
        for (MessageExt messageExt : list) {
            String msg1 = new String(messageExt.getBody());
            int queueId = messageExt.getQueueId();
            long queueOffset = messageExt.getQueueOffset();
            System.out.println("消费响应：queueId : " + queueId + ", queueOffset:" + queueOffset + ",  msgBody : " + msg1);
        }
    }
}
