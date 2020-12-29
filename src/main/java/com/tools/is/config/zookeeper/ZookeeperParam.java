package com.tools.is.config.zookeeper;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * zookeeper配置，从配置文件获取
 */
@ConfigurationProperties(prefix = "zookeeper.config")
@Getter
public class ZookeeperParam {

    private String server;
    private String namespace;
    private String digest;
    private Integer sessionTimeoutMs;
    private Integer connectionTimeoutMs;
    private Integer maxRetries;
    private Integer baseSleepTimeMs;
}
