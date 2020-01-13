package com.xhh.demo.feign.client.controller;

import com.xhh.demo.feign.client.client.DemoClient;
import feign.Feign;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DemoController
 *
 * @author tiger
 */
@Log4j2
@RestController
public class DemoController {

    @Value("${api.url}")
    private String url;

    @GetMapping("/version")
    public String version() {
        DemoClient demoClient = Feign.builder().target(DemoClient.class, url);
        return "response: " + demoClient.hello();
    }
}
