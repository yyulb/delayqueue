package com.tools.is.core;


import lombok.*;

/**
 * 延迟任务索引
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DelayJobIndex {

    /**
     * 任务的执行时间
     */
    private long delayTime;

    /**
     * 任务的唯一标识
     */
    private long jodId;


}
