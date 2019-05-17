package com.learn.mq;

import com.learn.mq.rocketMQ.Consumer;
import com.learn.mq.rocketMQ.Producer;
import org.junit.Test;

/**
 * RocketMQ是一款低延迟、高可靠、可伸缩、易于使用的消息中间件，具有以下特性：
 * 1. 支持发布订阅（pub/sub）和点对点（p2p）消息模型
 * 2. 在一个队列中可靠的先进先出和严格的顺序传递
 * 3. 支持拉（pull）和推（push）两种消息模式
 * 4. 单一队列百万消息的堆积能力
 * 5. 支持多种消息协议，jms/mqtt等
 * 6. 分布式高可用的部署架构，满足至少一次消息传递语义
 * 7. 提供docker镜像用于隔离测试和云集群部署
 * 8. 提供配置、指标和监控等丰富的dashboard
 */
public class RocketMQ {
    private static final String TOPIC = "TopicTest";

    /**
     * 1. 消息顺序问题：不解决，业务来控制顺序，若要解决，需要保证生产者-messagequeue-消费者是一对一。
     * <p>
     * 2. 消息重复问题：一样不解决，业务来控制
     * 消费端处理消息的业务逻辑保持幂等性
     * 保证每条消息都有唯一编号且保证消息处理成功与去重表的日志同时出现
     */
    @Test
    public void test() {
        String msg = "测试消息";
        Producer producer=new Producer();
        producer.send(msg, TOPIC);
    }


}
