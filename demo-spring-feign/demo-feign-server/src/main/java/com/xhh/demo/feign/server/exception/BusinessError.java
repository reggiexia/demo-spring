package com.xhh.demo.feign.server.exception;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * {@code BusinessError} is an annotation used by
 * {@link com.xhh.demo.feign.server.exception.handler.DemoExceptionHandler}. The global
 * exception will set http status code according to the annotation attached to a exception
 *
 * @author Zhao Kun
 */
@Retention(value = RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface BusinessError {

    @AliasFor("status")
    int value() default 500;

    /**
     * status code which will be set to http's status
     */
    @AliasFor("value")
    int status() default 500;

    /**
     * a human readable message indicated what's the error meaning
     *
     */
    String message() default "Server Internal Error";

    /**
     * a reference (url) refer to more details of the error
     *
     */
    String reference() default "";
}
