package com.learn.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 基于redis实现的分布式锁
 */
@Component
public class RedisLock {
    @Autowired
    private RedisTemplate redisTemplate;
    //锁的最大超时时间，默认一天
    private static final long finalDefaultTTLwithKey = 24 * 3600;
    //锁的默认超时时间，20秒
    private static final long defaultTime = 20;

    public boolean lock(String key, long expire) {
        long currentTime = System.currentTimeMillis();
        long expireTime = currentTime + expire;
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, expireTime);
        //获取锁成功，返回
        if (result) {
            redisTemplate.opsForValue().set(key, expire, finalDefaultTTLwithKey, TimeUnit.SECONDS);
            return true;
        }
        //取出当前的值
        Object value = redisTemplate.opsForValue().get(key);
        //值不存在，获取锁失败
        if (null == value) {
            return false;
        }
        //锁还在有效期内，获取锁失败
        currentTime = System.currentTimeMillis();
        //假设两个线程同时进来这里，因为key被占用了，而且锁过期了。oldExpireTime=A(get取的旧的值肯定是一样的),两个线程的value都是B,key都是K.锁时间已经过期了。
        //而这里面的getAndSet一次只会一个执行，也就是一个执行之后，上一个的value已经变成了B。只有一个线程获取的上一个值会是A，另一个线程拿到的值是B。
        long currentExpireTime = (long) value;
        if (currentExpireTime < currentTime) {
            return false;
        }
        //超时，重新获取上一个锁，旧址
        long oldExpireTime = (long) redisTemplate.opsForValue().getAndSet(key, expireTime);
        //防止并发
        if (oldExpireTime == currentExpireTime) {
            return true;
        }
        return false;
    }

    public boolean unLock(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        Boolean result = true;
        if (null != value) {
            result = redisTemplate.delete(key);
        }
        return result;
    }
}
