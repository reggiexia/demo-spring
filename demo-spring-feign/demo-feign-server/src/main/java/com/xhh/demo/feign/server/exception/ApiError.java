package com.xhh.demo.feign.server.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ApiError {
    private long timestamp;
    private String message;
    private String reference;
    private int status;

    public ApiError(int status, String message) {
        this(status, message, "");
    }

    public ApiError(int status, String message, String reference) {
        this(status, new Date().getTime(), message, reference);
    }

    ApiError(int status, long timestamp, String message, String reference) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
        this.reference = reference;
    }
}
