package com.tools.is.utils;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

/**
 * redisson 工具类
 */
@Slf4j
public class RedissonUtils {
    

    private static RedissonClient redissonClient;

    static {
        try {
            Config config = Config.fromYAML(RedissonUtils.class.getClassLoader().getResource("redis.yaml"));
            redissonClient = Redisson.create(config);
            log.info("redisconfig:{} ",config.toYAML());
        }catch (Exception e){
            log.error("initialization RedissosnClient error :",e);
        }

    }

    /**
     * 获取 redissonClient
     * @return redissonclient
     */
    public static RedissonClient getRedissonClient(){
        return redissonClient;
    }


    /**
     * 关闭 redissonClient
     * @return
     */
    public static void close(){
        redissonClient.shutdown();
    }

    /**
     * 通用对象桶
     * Redisson的分布式RBucketJava对象是一种通用对象桶可以用来存放任类型的对象*
     * @param <V>        泛型
     * @param objectName 对象名
     * @return RBucket
     */
    public static <V>RBucket<V> getRBucket(String objectName){
        return redissonClient.getBucket(objectName);
    }

    /**
     * 获取map对象
     *
     * @param <K>        the type parameter
     * @param <V>        the type parameter
     * @param objectName the object name
     * @return the r map
     */
    public static <K,V>RMap<K,V> getMap(String objectName){
        return redissonClient.getMap(objectName);
    }


    /**
     * 获取ScoredSortedSett对象
     * @param objectName
     * @param <V>
     * @return
     */
    public static <V> RScoredSortedSet<V> getScoredSorteSet(String objectName) {
        return redissonClient.getScoredSortedSet(objectName);
    }

    /**
     * 获取list对象
     *
     * @param <V>        the type parameter
     * @param objectName the object name
     * @return the r list
     */
    public static <V> RList<V> getList(String objectName){
        return redissonClient.getList(objectName);
    }

    /**
     * 获取queue对象
     *
     * @param <V>        the type parameter
     * @param objectName the object name
     * @return the r queue
     */
    public static <V> RQueue<V> getQueue(String objectName){
        return redissonClient.getQueue(objectName);
    }


    /**
     * Get blocking queue r blocking queue.
     *
     * @param <V>        the type parameter
     * @param objectName the object name
     * @return the r blocking queue
     */
    public static <V> RBlockingQueue<V> getBlockingQueue(String objectName){
        return redissonClient.getBlockingQueue(objectName);
    }

    /**
     * Get atomic long r atomic long.
     *
     * @param objectName the object name
     * @return the r atomic long
     */
    public static RAtomicLong getAtomicLong(String objectName){
        return redissonClient.getAtomicLong(objectName);
    }

}
