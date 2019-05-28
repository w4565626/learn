package com.learn.engineer;

/**
 * 高并发系统中的限流算法：
 * 计数器算法（1分钟次数不能大于100）：
 * 1. 设置一个计数器count
 * 2. 每当一个接收请求，count+1
 * 3. 如果count大于100，并且该请求和第一个请求间隔在1分钟之内，说明请求过多。
 * 4. 如果count在100之内，并且间隔大于1分钟，说明count在限流范围之内，重置count；
 * 问题：临界问题，在0.59发送100个请求，然后1.00在发送100个请求（此时count已重置）。限流失效。
 *
 * 滑窗计数法：
 * 1. 设置一个时间窗口为1分钟，将此窗口分成6格，每格10秒。
 * 2. 每过10秒，时间窗口向前滑动一格，格子都有独立的计数器count
 * 3. 解决临界问题：0.59的100个请求落在50-60这个窗口，当时间到达1：00，窗口往前移动一格，
 * 所以1：00到达的100个请求落在窗口内，此时窗口内有200个请求。触发了限流。
 * 4. 窗口的格子越多，窗口越平滑，限流的统计越精确。
 * 5. 问题：占用内存大，需要维护这个时间窗口，无法平滑限流，时间复杂度高。
 *
 * 漏桶算法：
 * 1. 思路：水（请求）先进入到漏桶里，漏桶以一定速度出水（接口有响应速率），当水流速率过大直接溢出（访问频率超过接口响应速率），触发限流。
 * 2. 优点：实现了平滑限流，时间复杂度低。
 * 3. 问题：因为漏桶的漏出速率时固定的，对于突发的大流量，无法处理
 *
 * 令牌桶算法：
 * 1. 思路：以一定的速率生成令牌放入桶中，如果桶满，则暂停。每个请求需要拿到令牌。请求拿不到令牌，触发限流。
 * 2. 优点：解决了漏桶算法无法处理瞬时流量的问题。令牌桶是进水，漏桶是出水。
 */
public class CurrentLimit {
}