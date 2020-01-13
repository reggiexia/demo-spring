package com.xhh.demo.feign.client.client;

import feign.RequestLine;

/**
 * demo-spring
 *
 * @author tiger
 */
public interface DemoClient {

    @RequestLine("GET /hello")
    String hello();
}
