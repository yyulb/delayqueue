package com.tools.is.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 延迟消息体
 */
@Getter
@Setter
@AllArgsConstructor
@ApiModel("延迟消息体")
public class DelayMessage {

    @ApiModelProperty(value = "任务类型",required = true)
    @NotBlank(message = "任务类型不能为空")
    private String topic;

    @ApiModelProperty(value = "延迟任务执行时间（13位时间时间戳）",required = true)
    @NotNull(message = "延迟任务执行时间不能为空")
    private Long delayTime;

    @ApiModelProperty(value = "延迟任务执行超时时间（单位：秒）",required = true)
    @NotNull(message = "延迟任务执行超时时间不能为空")
    private Long ttrTime;

    @ApiModelProperty(value = "消息内容",required = true)
    @NotBlank(message = "消息内容不能为空")
    @Length(max = 300,message = "消息内容长度不能超过300字符")
    private String message;
}
