package com.tools.is.core;


import com.tools.is.utils.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScoredSortedSet;

/**
 * 延迟任务筒
 */
@Slf4j
public class DelayBucket {


    /**
     * 添加 DelayJob 到 延迟任务桶中
     * @param key
     * @param delayJobIndex
     */
    public static void addToBucket(String key,DelayJobIndex delayJobIndex) {
        RScoredSortedSet<DelayJobIndex> scoredSorteSet = RedissonUtils.getScoredSorteSet(key);
        scoredSorteSet.add(delayJobIndex.getDelayTime(),delayJobIndex);
    }

    /**
     * 从延迟任务桶中获取延迟时间最小的 jodId
     * @param key
     * @return
     */
    public static DelayJobIndex getFromBucket(String key) {
        RScoredSortedSet<DelayJobIndex> scoredSortedSet = RedissonUtils.getScoredSorteSet(key);
        return scoredSortedSet.size() == 0 ? null : scoredSortedSet.first();
    }

    /**
     * 从延迟任务桶中删除 jodId
     * @param key
     * @param delayJobIndex
     */
    public static void deleteFormBucket(String key,DelayJobIndex delayJobIndex) {
        RScoredSortedSet<DelayJobIndex> scoredSorteSet = RedissonUtils.getScoredSorteSet(key);
        scoredSorteSet.remove(delayJobIndex);
    }
}
