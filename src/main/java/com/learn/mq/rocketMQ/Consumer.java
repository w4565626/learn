package com.learn.mq.rocketMQ;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {
    private static final String url = "192.168.11.242:9876";

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.receive("TopicTest");
    }

    //普通消息消费
    public void receive(String topic) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_4");
        consumer.setNamesrvAddr(url);
        String msg = null;
        try {
            //设置消费者端消息拉取策略，表示从哪里开始消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //设置消费者拉取消息的策略，*表示消费该topic下的所有消息，也可以指定tag进行消息过滤
            consumer.subscribe(topic, "*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    for (MessageExt messageExt : list) {
                        String topic1 = messageExt.getTopic();
                        String tag = messageExt.getTags();
                        String msg1 = new String(messageExt.getBody());
                        System.out.println("*********************************");
                        System.out.println("消费响应：msgId : " + messageExt.getMsgId() + ",  msgBody : " + msg1 + ", tag:" + tag + ", topic:" + topic1);
                        System.out.println("*********************************");
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
            System.out.println("Consumer Started....");
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }
}
