package com.tools.is.config.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * ZookeeperConfig
 */
@Configuration
@Slf4j
public class ZookeeperConfig {

    @Resource
    private ZookeeperParam zookeeperParam;
    private static CuratorFramework client = null;

    /**
     * 初始化
     */
    @PostConstruct
    public void init (){
        //重试策略
        RetryPolicy policy = new ExponentialBackoffRetry(
                zookeeperParam.getBaseSleepTimeMs(),
                zookeeperParam.getMaxRetries());
        //通过工厂创建Curator
        client = CuratorFrameworkFactory.builder()
                .connectString(zookeeperParam.getServer())
                .namespace(zookeeperParam.getNamespace())
                .authorization("digest",zookeeperParam.getDigest().getBytes())
                .connectionTimeoutMs(zookeeperParam.getConnectionTimeoutMs())
                .sessionTimeoutMs(zookeeperParam.getSessionTimeoutMs())
                .retryPolicy(policy).build();
        //开启连接
        client.start();
        log.info("zookeeper init finish...");
    }

    public static CuratorFramework getClient (){
        return client;
    }

    public static void closeClient (){
        if (client != null){
            client.close();
        }
    }
}
