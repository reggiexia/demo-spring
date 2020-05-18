package com.xhh.demo.jedis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * eureka server
 *
 * @author tiger
 */
@SpringBootApplication
public class JedisServer {

    public static void main(String[] args) {
        SpringApplication.run(JedisServer.class, args);
    }
}
