package com.xhh.demo.cloud.resilience4j.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * resilience4j server
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2019-12-24 16:51
 */
@EnableConfigServer
@SpringBootApplication
public class Resilience4jServer {

    public static void main(String[] args) {
        SpringApplication.run(Resilience4jServer.class, args);
    }

}
