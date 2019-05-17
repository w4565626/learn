package com.learn.mq.rocketMQ;


import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class Producer {
    private static final String url = "192.168.11.242:9876";

    /**
     * 普通发送消息
     *
     * @param msg
     * @param topic
     */
    public  void send(String msg, String topic) {

        try {
            DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
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
        }
    }

    /**
     * 选择队列发送消息
     *
     * @param msg
     * @param topic
     */
    public static void send(String msg, String topic, int queue) {

        try {
            DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
            ////设置NameServer地址,此处应改为实际NameServer地址，多个地址之间用；分隔
            producer.setNamesrvAddr(url);
            //为避免程序启动的时候报错，添加此代码，可以让rocketMq自动创建topickey
            producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");
            producer.start();
            Message message = new Message(topic, msg.getBytes());
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    for (MessageQueue messageQueue : list) {
                        if (messageQueue.getQueueId() == queue) {
                            return messageQueue;
                        }
                    }
                    return list.get(0);
                }
            }, queue);
            System.out.println("发送的消息ID:" + sendResult.getMsgId() + "--- 发送消息的状态：" + sendResult.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}