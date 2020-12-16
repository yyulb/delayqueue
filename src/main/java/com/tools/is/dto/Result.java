package com.tools.is.dto;


import com.tools.is.enums.ResultType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {

    private int code;
    private String message;
    private T result;

    public Result() {
    }

    public Result(T result, Integer code, String message) {
        this.result = result;
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> Result<T> error() {
        return new Result((Object)null, ResultType.FAIL.getCode(), ResultType.FAIL.getMessage());
    }

    public static <T> Result<T> error(String message) {
        return new Result((Object)null, ResultType.FAIL.getCode(), message);
    }

    public static <T> Result<T> ok() {
        return new Result((Object)null, ResultType.SUCCESS.getCode(), ResultType.SUCCESS.getMessage());
    }

    public static <T> Result<T> ok(T data) {
        return new Result(data, ResultType.SUCCESS.getCode(), ResultType.SUCCESS.getMessage());
    }

}
