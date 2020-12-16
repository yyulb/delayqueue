package com.tools.is.exception;

/**
 * 自定义异常
 */
public class BusinessException extends RuntimeException {

    private BusinessErrorInterface businessErrorInterface;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(BusinessErrorInterface businessErrorInterface) {
        super(businessErrorInterface.getMessage());
        this.businessErrorInterface = businessErrorInterface;
    }

    public BusinessException(String message, BusinessErrorInterface businessErrorInterface) {
        super(message);
        this.businessErrorInterface = businessErrorInterface;
    }

}
