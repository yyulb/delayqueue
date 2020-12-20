package com.tools.is.listeners;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tools.is.core.DelayBucketHandler;
import com.tools.is.core.DelayQueue;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.concurrent.*;

/**
 * 系统启动的时候创建存放延迟任务索引的桶和处理延迟任务的线程
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    private static final Long KEEPALIVED_TIME = 0L;

    private static final Integer BLOCKQUEUE_CAPACITY = 10;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ExecutorService executorService = new ThreadPoolExecutor((int) DelayQueue.DELAY_BUCKET_NUM, (int) DelayQueue.DELAY_BUCKET_NUM,
                KEEPALIVED_TIME, TimeUnit.SECONDS,
                new ArrayBlockingQueue(BLOCKQUEUE_CAPACITY),new ThreadFactoryBuilder()
                .setNameFormat("delaybuckethandler-pool-%s").build(),new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < DelayQueue.DELAY_BUCKET_NUM; i++) {
            executorService.execute(new DelayBucketHandler(DelayQueue.DELAY_BUCKET_KEY_PREFIX + i));
        }
    }

}
