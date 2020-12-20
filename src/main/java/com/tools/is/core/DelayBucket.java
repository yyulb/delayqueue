package com.tools.is.core;


import com.tools.is.utils.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScoredSortedSet;

/**
 * 延迟任务桶
 */
@Slf4j
public class DelayBucket {


    /**
     * 添加 DelayJob 到 延迟任务桶中
     * @param key
     * @param delayQueueJobIndex
     */
    public static boolean addToBucket(String key, DelayQueueJobIndex delayQueueJobIndex) {
        RScoredSortedSet<DelayQueueJobIndex> scoredSorteSet = RedissonUtils.getScoredSorteSet(key);
        return scoredSorteSet.add(delayQueueJobIndex.getDelayTime(), delayQueueJobIndex);
    }

    /**
     * 从延迟任务桶中获取延迟时间最小的 jodId
     * @param key
     * @return
     */
    public static DelayQueueJobIndex getFromBucket(String key) {
        RScoredSortedSet<DelayQueueJobIndex> scoredSortedSet = RedissonUtils.getScoredSorteSet(key);
        return scoredSortedSet.size() == 0 ? null : scoredSortedSet.first();
    }

    /**
     * 从延迟任务桶中删除 jodId
     * @param key
     * @param delayQueueJobIndex
     */
    public static boolean deleteFormBucket(String key, DelayQueueJobIndex delayQueueJobIndex) {
        RScoredSortedSet<DelayQueueJobIndex> scoredSorteSet = RedissonUtils.getScoredSorteSet(key);
        return scoredSorteSet.remove(delayQueueJobIndex);
    }
}
