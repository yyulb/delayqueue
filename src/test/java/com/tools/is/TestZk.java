package com.tools.is;

import com.tools.is.service.ZookeeperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *测试操作zk
 */
@SpringBootTest(classes = StartApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class TestZk {

    @Autowired
    private ZookeeperService zookeeperService;

    @Test
    public void test(){
        zookeeperService.createNodeAndData(CreateMode.PERSISTENT,"testzk","test");
    }
}
