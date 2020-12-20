package com.tools.is.controller;


import com.tools.is.core.DelayQueue;
import com.tools.is.core.DelayQueueJob;
import com.tools.is.dto.DelayMessage;
import com.tools.is.dto.Result;
import com.tools.is.utils.GsonUtil;
import com.tools.is.utils.SnowflakeIdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api("延迟队列相关接口")
@RestController
@Slf4j
public class DelayQueueController {


    @ApiOperation("添加延迟任务")
    @PostMapping(value = "/push")
    public Result push(@RequestBody @Valid DelayMessage delayMessage) {
        DelayQueueJob delayQueueJob = new DelayQueueJob();
        delayQueueJob.setTopic(delayMessage.getTopic());
        delayQueueJob.setDelayTime(delayMessage.getDelayTime());
        delayQueueJob.setMessage(delayMessage.getMessage());
        delayQueueJob.setTtrTime(delayMessage.getTtrTime());
        delayQueueJob.setId(SnowflakeIdUtil.nextId());
        DelayQueue.push(delayQueueJob);
        log.info("push success delayQueueJob = {}", GsonUtil.GsonString(delayQueueJob));
        return Result.ok();
    }

    @ApiOperation("轮询队列获取任务")
    @GetMapping(value = "/pop/{topic}")
    public Result pop(@PathVariable("topic") String topic) {
        DelayQueueJob delayQueueJob = DelayQueue.pop(topic);
        log.info("pop success delayQueueJob = {}", GsonUtil.GsonString(delayQueueJob));
        return Result.ok(delayQueueJob);
    }

    @ApiOperation("完成任务")
    @PostMapping(value = "/finish")
    public Result finish(@ApiParam(name = "id", value = "任务id", required = true) @RequestParam("id") Long id) {
        DelayQueue.finish(id);
        log.info("finish success id = {}",id);
        return Result.ok();
    }
}
