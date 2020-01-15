package com.xhh.demo.feign.client.config;

import com.xhh.demo.feign.client.client.DemoClient;
import feign.Feign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * demo-spring
 *
 * @author tiger
 */
@Configuration
public class ClientConfig {

    @Value("${api.url}")
    private String url;

    @Bean
    public DemoClient demoClient() {
        return Feign.builder().target(DemoClient.class, url);
    }
}
