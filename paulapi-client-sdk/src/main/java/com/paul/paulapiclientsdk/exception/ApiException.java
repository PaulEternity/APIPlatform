package com.paul.paulapiclientsdk.exception;

import com.paul.paulapicommon.common.ErrorCode;

public class ApiException extends Exception{
    private static final long serialVersionUID = 1L;
    private int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public ApiException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode(){
        return code;
    }
}
