package com.learn.mq;

import com.learn.mq.rocketMQ.Consumer;
import com.learn.mq.rocketMQ.OrderDemo;
import com.learn.mq.rocketMQ.Producer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
 *
 * 消息存储机制：消息保存在磁盘的commitlog文件中，保证磁盘顺序写，就能提高效率
 * consumeQueue是消息的逻辑队列，类似数据库的索引文件。每个topic每个message queue对应一个consumeQueue文件
 *
 * 高可用机制：
 * 消费者：master和slave配合使用，master支持读写，slave只读。
 * 生产者：创建topic时，把topic的多个message queue创建在多个broker组上。
 * 备注：不支持slave自动选举转化为master机制。
 *
 * 刷盘机制：
 * 同步刷盘：在返回写成功状态时，消息已经被写入磁盘。效率低，但是数据不会丢失。
 * 异步刷盘：在返回写成功状态时，消息只是被写入了内存，当内存的消息累积到一定量，触发写磁盘操作。快速，吞吐量大，但是存在数据丢失风险。
 *
 * 复制机制（master复制到slave）：
 * 同步复制：等master和slave全部复制成功才返回写成功状态
 * 异步复制：只要master写成功即返回写成功状态
 *
 * 实际场景：异步刷盘，同步复制，保证即使一个机器故障，数据任然不会丢失。
 *
 */
public class RocketMQ {
    private static final String TOPIC = "TopicTest";


    /**
     * 发送普通消息
     */
    @Test
    public void test() {
        String msg = "测试消息";
        Producer producer = new Producer();
        producer.send(msg, TOPIC);
    }

    /**
     * 1. 消息顺序问题：不解决，业务来控制顺序，若要解决，需要保证生产者-messagequeue-消费者是一对一。
     * <p>
     * 2. 消息重复问题：一样不解决，业务来控制
     * 消费端处理消息的业务逻辑保持幂等性
     * 保证每条消息都有唯一编号且保证消息处理成功与去重表的日志同时出现
     */
    //发送顺序消息
    @Test
    public void test1() {
        Producer producer = new Producer();
        producer.send(TOPIC);
    }

    /**
     * 事务消息：采用两阶段提交
     * 1. 发送方向mq发送"待确认"消息
     * 2.mq收到"待确认"消息并持久化，回复发送方成功。一阶段完成
     * 3.发送方开始执行本地事务逻辑
     * 4.发送方根据执行结果向MQ发送"二次确认"（commit或者rollback）消息,mq根据状态决定一阶段消息是可投递或者要回滚。
     * 5.若出现异常，二次确认消息未到达mq，服务器发起会查请求。
     * 6.发送方收到回查请求，通过检查对应消息的本地事件执行结果返回commit或者rollback状态。
     * 7.按照步骤4处理
     *
     */
    @Test
    public void test2() {
        String msg = "测试消息";
        Producer producer = new Producer();
        producer.sendTransaction(msg,TOPIC);
    }
}
