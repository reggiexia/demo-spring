package com.xhh.demo.lettuce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * eureka server
 *
 * @author tiger
 */
@SpringBootApplication
public class LettuceServer {

    public static void main(String[] args) {
        SpringApplication.run(LettuceServer.class, args);
    }
}
