package com.tools.is.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultType {

    SUCCESS(0, "success"),
    BAD_REQUEST(-4, "bad request"),
    FAIL(1, "fail");

    private Integer code;
    private String message;

}
