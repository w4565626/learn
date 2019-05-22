package com.learn.redis;

/**
 * redis:高性能的是一个高性能的key-value数据库
 * 特点：
 * 1. 支持数据的持久化，可以将内存中的数据保存在磁盘中，重启的时候可以再次加载进行使用。
 * 2. 不仅仅支持简单的key-value类型的数据，同时还提供list，set，zset，hash等数据结构的存储。
 * 3. 支持数据的备份，即master-slave模式的数据备份。
 * <p>
 * 优势：
 * 1. 性能极高 – Redis能读的速度是110000次/s,写的速度是81000次/s 。
 * 2. 丰富的数据类型 – Redis支持二进制案例的 Strings, Lists, Hashes, Sets 及 Ordered Sets 数据类型操作
 * 3. 原子 – Redis的所有操作都是原子性的，意思就是要么成功执行要么失败完全不执行。
 * 单个操作是原子性的。多个操作也支持事务，即原子性，通过MULTI和EXEC指令包起来。
 * 4. 丰富的特性 – Redis还支持 publish/subscribe, 通知, key 过期等等特性。
 * <p>
 * 持久化方式：
 * RDB：在指定的时间间隔对数据做快照存储
 * AOF：记录每次写操作的命令，服务重启可以执行这些命令来恢复
 * <p>
 * 架构模式：
 * 1.单机模式：简单一个机器，容易出现单点故障，性能差
 * 2.主从复制：master/slave模式。redis复制功能允许将master的数据复制到slave。
 *
 * 3.哨兵：Redis sentinel 是一个分布式系统中监控 redis 主从服务器，并在主服务器下线时自动进行故障转移
 * 特性:监控（Monitoring）：    Sentinel  会不断地检查你的主服务器和从服务器是否运作正常。
 * 提醒（Notification）： 当被监控的某个 Redis 服务器出现问题时， Sentinel 可以通过 API 向管理员或者其他应用程序发送通知。
 * 自动故障迁移（Automatic failover）： 当一个主服务器不能正常工作时， Sentinel 会开始一次自动故障迁移操作。
 *
 * 4. 集群（proxy 型）：Twemproxy 是一个 Twitter 开源的一个 redis 和 memcache 快速/轻量级代理服务器
 * 特点：1、多种 hash 算法：MD5、CRC16、CRC32、CRC32a、hsieh、murmur、Jenkins
 * 2、支持失败节点自动删除
 * 3、后端 Sharding 分片逻辑对业务透明，业务方的读写方式和操作单个 Redis 一致
 * 缺点：增加了新的 proxy，需要维护其高可用。
 *
 * 5. 集群（直连型）：从redis 3.0之后版本支持redis-cluster集群，Redis-Cluster采用无中心结构，
 * 每个节点保存数据和整个集群状态,每个节点都和其他所有节点连接。
 * 特点：
 * 1、无中心架构（不存在哪个节点影响性能瓶颈），少了 proxy 层。
 * 2、数据按照 slot 存储分布在多个节点，节点间数据共享，可动态调整数据分布。
 * 3、可扩展性，可线性扩展到 1000 个节点，节点可动态添加或删除。
 * 4、高可用性，部分节点不可用时，集群仍可用。通过增加 Slave 做备份数据副本
 * 5、实现故障自动 failover，节点之间通过 gossip 协议交换状态信息，用投票机制完成 Slave到 Master 的角色提升。
 * 缺点：
 * 1、资源隔离性较差，容易出现相互影响的情况。
 * 2、数据通过异步复制,不保证数据的强一致性
 */
public class Redis {

    /**
     *  * 问：redis是单线程的，为啥能支持高并发？
     *  * 答：redis实现了IO多路复用
     */

    /**
     * 集群（直连型）实现：
     * 1. redis-cluster把所有物理节点映射在（0-16383）slot（哈希槽）上，cluster 负责维护node<->slot<->value
     * 2.集群实现分配好16383个桶，当需要在集群中set操作时，根据CRC16('key')%16384的值，决定将key放在哪个桶中。
     */
}
