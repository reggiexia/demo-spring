package com.xhh.demo.prometheus;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动入口
 *
 * @author tiger
 * @version 1.0.0 createTime: 2017/4/17 下午3:03
 */
@SpringBootApplication
@EnablePrometheusEndpoint
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
