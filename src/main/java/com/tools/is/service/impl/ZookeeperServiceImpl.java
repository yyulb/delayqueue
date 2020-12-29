package com.tools.is.service.impl;

import com.tools.is.config.zookeeper.ZookeeperConfig;
import com.tools.is.service.ZookeeperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ZookeeperServiceImpl implements ZookeeperService {


    @Override
    public boolean isExistNode(String path) {
        CuratorFramework client = ZookeeperConfig.getClient();
        client.sync() ;
        try {
            return client.checkExists().forPath(path) != null;
        } catch (Exception e) {
            log.error("isExistNode error = {}", e);
        }
        return false;
    }

    @Override
    public void createNode(CreateMode mode, String path) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        try {
            // 递归创建所需父节点
            client.create().creatingParentsIfNeeded().withMode(mode).forPath(path);
        } catch (Exception e) {
            log.error("createNode error = {}", e);
        }
    }

    @Override
    public void setNodeData(String path, String nodeData) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        try {
            // 设置节点数据
            client.setData().forPath(path, nodeData.getBytes("UTF-8"));
        } catch (Exception e) {
            log.error("setNodeData error = {}", e);
        }
    }

    @Override
    public void createNodeAndData(CreateMode mode, String path, String nodeData) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        try {
            // 创建节点，关联数据
            client.create().creatingParentsIfNeeded().withMode(mode)
                    .forPath(path,nodeData.getBytes("UTF-8"));
        } catch (Exception e) {
            log.error("createNode error = {}", e);
        }
    }

    @Override
    public String getNodeData(String path) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        try {
            // 数据读取和转换
            byte[] dataByte = client.getData().forPath(path) ;
            return new String(dataByte,"UTF-8") ;
        }catch (Exception e) {
            log.error("getNodeData error = {}", e);
        }
        return null;
    }


    @Override
    public List<String> getNodeChild(String path) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        List<String> nodeChildDataList = new ArrayList<>();
        try {
            // 节点下数据集
            nodeChildDataList = client.getChildren().forPath(path);
        } catch (Exception e) {
            log.error("getNodeChild error = {}", e);
        }
        return nodeChildDataList;
    }


    @Override
    public void deleteNode(String path, Boolean recursive) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        try {
            if(recursive) {
                // 递归删除节点
                client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
            } else {
                // 删除单个节点
                client.delete().guaranteed().forPath(path);
            }
        } catch (Exception e) {
            log.error("deleteNode error = {}", e);
        }
    }


    @Override
    public InterProcessReadWriteLock getReadWriteLock(String path) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        // 写锁互斥、读写互斥
        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, path);
        return readWriteLock ;
    }

}
