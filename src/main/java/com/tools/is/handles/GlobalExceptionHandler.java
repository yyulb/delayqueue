package com.tools.is.handles;

import com.tools.is.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        log.error("system error = {}",e);
        return Result.error(e.getMessage());
    }
}
