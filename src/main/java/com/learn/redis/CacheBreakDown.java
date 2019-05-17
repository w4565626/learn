package com.learn.redis;

/**
 * 热点key:某个访问非常频繁的key,例如重大新闻。缓存的构建需要时间
 * 解决方案：
 * 1.缓存永不过期：不设置过期时间，将过期时间设置在value里面。
 * 2.互斥锁：保证只有一个线程在构建缓存
 */
public class CacheBreakDown {

}
