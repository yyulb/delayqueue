package com.tools.is.core;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 扫描延迟任务桶中的任务，放到准备队列中
 */
@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class DelayBucketHandler implements Runnable {


    private String delayBucketKey;


    @Override
    public void run() {
        while (true) {
            try {
                DelayQueueJobIndex jobIndex = DelayBucket.getFromBucket(this.delayBucketKey);
                //没有任务
                if (jobIndex == null) {
                    sleep();
                    continue;
                }
                //延迟时间没到
                if (jobIndex.getDelayTime() > System.currentTimeMillis()) {
                    sleep();
                    continue;
                }

                DelayQueueJob delayQueueJod = DelayQueueJobPool.getDelayQueueJod(jobIndex.getJodId());
                //延迟任务元数据不存在
                if (delayQueueJod == null) {
                    DelayBucket.deleteFormBucket(this.delayBucketKey,jobIndex);
                    continue;
                }

                //再次确认延时时间是否到了,如果没到删除旧的计算新的执行时间重新放入
                if (delayQueueJod.getDelayTime() > System.currentTimeMillis()) {
                    DelayBucket.deleteFormBucket(this.delayBucketKey,jobIndex);
                    DelayBucket.addToBucket(this.delayBucketKey, new DelayQueueJobIndex(delayQueueJod.getId(), delayQueueJod.getDelayTime()));
                } else {
                    ReadyQueue.pushToReadyQueue(delayQueueJod.getTopic(),delayQueueJod.getId());
                    DelayBucket.deleteFormBucket(this.delayBucketKey,jobIndex);
                }

            }catch (Exception e) {
                log.error("run error = {}",e);
            }


        }
    }

    private void sleep(){
        try {
            TimeUnit.SECONDS.sleep(1L);
        }catch (InterruptedException e){
            log.error("sleep error = {}",e);
        }
    }

}
