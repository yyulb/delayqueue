package com.tools.is.core;

import lombok.extern.slf4j.Slf4j;



/**
 * 延迟消息队列
 */
@Slf4j
public class DelayQueue {

    public static final String DELAY_BUCKET_KEY_PREFIX = "delayBucket";
    public static final long  DELAY_BUCKET_NUM = 10L;

    /**
     * 获取delayBucket key 分开多个，有利于提高效率
     * @param delayQueueJodId
     * @return
     */
    private static String getDelayBucketKey(Long delayQueueJodId) {
        return DELAY_BUCKET_KEY_PREFIX + Math.floorMod(delayQueueJodId,DELAY_BUCKET_NUM);
    }

    /**
     * 添加延迟任务到延迟队列
     * @param delayQueueJob
     */
    public static void push(DelayQueueJob delayQueueJob) {
        Long jobId = delayQueueJob.getId();
        DelayQueueJobPool.addDelayQueueJod(delayQueueJob);
        DelayQueueJobIndex jobIndex = new DelayQueueJobIndex(delayQueueJob.getDelayTime(),jobId);
        boolean addResult = DelayBucket.addToBucket(getDelayBucketKey(jobId), jobIndex);
        log.info("push jobId = {},addResult = {}",jobId,addResult);
    }

    /**
     * 获取准备好的延迟任务，并用ttr时间添加一个新的延迟任务，如果在ttr时间范围内执行完成则这个新的延迟任务会被删除（确保消息执行）
     * @param topic
     * @return
     */
    public static DelayQueueJob pop(String topic) {
        Long delayQueueJodId = ReadyQueue.pollFormReadyQueue(topic);
        if (delayQueueJodId == null) {
            return null;
        } else {
            DelayQueueJob delayQueueJod = DelayQueueJobPool.getDelayQueueJod(delayQueueJodId);
            if (delayQueueJod == null) {
                return null;
            } else {
                Long delayTime = delayQueueJod.getDelayTime();
                //获取消费超时时间，重新放到延迟任务桶中
                Long reDelayTime = System.currentTimeMillis() + delayQueueJod.getTtrTime() * 1000L;
                delayQueueJod.setDelayTime(reDelayTime);
                DelayQueueJobPool.addDelayQueueJod(delayQueueJod);
                DelayQueueJobIndex jobIndex = new DelayQueueJobIndex(reDelayTime,delayQueueJod.getId());
                DelayBucket.addToBucket(getDelayBucketKey(delayQueueJod.getId()),jobIndex);
                //返回的时候设置回原来的执行时间
                delayQueueJod.setDelayTime(delayTime);
                return delayQueueJod;
            }
        }
    }

    /**
     * 删除延迟队列任务
     * @param delayQueueJodId
     */
    public static void delete(Long delayQueueJodId) {
        DelayQueueJobPool.deleteDelayQueueJod(delayQueueJodId);
    }

    /**
     * 业务要在ttr时间内执行完并调用finish方法，否则延迟任务会重复执行
     * @param delayQueueJodId
     */
    public static void finish(Long delayQueueJodId) {
        DelayQueueJob delayQueueJod = DelayQueueJobPool.getDelayQueueJod(delayQueueJodId);
        if (delayQueueJod == null) {
            return;
        }
        DelayQueueJobPool.deleteDelayQueueJod(delayQueueJodId);
        DelayQueueJobIndex jobIndex = new DelayQueueJobIndex(delayQueueJod.getDelayTime(),delayQueueJod.getId());
        DelayBucket.deleteFormBucket(getDelayBucketKey(delayQueueJod.getId()),jobIndex);
    }

    /**
     * 查询delay job
     * @param delayQueueJodId
     * @return
     */
    public static DelayQueueJob get(Long delayQueueJodId) {
        return DelayQueueJobPool.getDelayQueueJod(delayQueueJodId);
    }
}
