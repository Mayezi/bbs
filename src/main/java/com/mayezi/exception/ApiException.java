package com.mayezi.exception;

import lombok.Setter;

@Setter
public class ApiException extends Exception {

    private int code;
    private String message;

    public ApiException(String message) {
        this.code = ErrorCode.error;
        this.message = message;
    }

    public ApiException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
