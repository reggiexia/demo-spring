package com.xhh.demo.feign.server.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ApiError {
    private String code;
    private long timestamp;
    private String message;
    private String reference;
    private int status;

    public ApiError(int status, String code, String message) {
        this(status, code, message, "");
    }

    public ApiError(int status, String code, String message, String reference) {
        this(status, code, new Date().getTime(), message, reference);
    }

    ApiError(int status, String code, long timestamp, String message, String reference) {
        this.status = status;
        this.code = code;
        this.timestamp = timestamp;
        this.message = message;
        this.reference = reference;
    }
}
