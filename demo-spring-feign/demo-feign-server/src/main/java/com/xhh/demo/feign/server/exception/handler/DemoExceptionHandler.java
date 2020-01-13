package com.xhh.demo.feign.server.exception.handler;

import com.xhh.demo.feign.server.exception.ApiError;
import com.xhh.demo.feign.server.exception.BusinessError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.annotation.Annotation;
import java.util.Optional;

@ControllerAdvice
public class DemoExceptionHandler {

    public final BusinessError defaultBusinessError = getDefaultBusinessErrorInstance();

    private BusinessError getDefaultBusinessErrorInstance() {
        return new BusinessError() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return BusinessError.class;
            }

            @Override
            public int value() {
                return this.status();
            }

            @Override
            public int status() {
                return HttpStatus.INTERNAL_SERVER_ERROR.value();
            }

            @Override
            public String code() {
                return "10000";
            }

            @Override
            public String message() {
                return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            }

            @Override
            public String reference() {
                return "";
            }
        };
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ApiError> processException(Exception ex) {
        BusinessError errorMetaData = Optional
                .ofNullable(ex.getClass().getAnnotation(BusinessError.class))
                .orElse(defaultBusinessError);

        return ResponseEntity
                .status(errorMetaData.status())
                .body(new ApiError(errorMetaData.status(), errorMetaData.code(), ex.getMessage(),
                        errorMetaData.reference()));
    }
}
