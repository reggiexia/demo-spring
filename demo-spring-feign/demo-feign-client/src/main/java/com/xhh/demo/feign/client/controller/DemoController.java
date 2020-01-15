package com.xhh.demo.feign.client.controller;

import com.xhh.demo.feign.client.client.DemoClient;
import feign.Feign;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private DemoClient demoClient;

    @GetMapping("/version")
    public String version() {
        return "get /version, response: " + demoClient.hello();
    }

    @GetMapping("/api/version")
    public String ver() {
        return "get /api/version, response: " + demoClient.hello();
    }

    @GetMapping("/api/error")
    public String error() {
        String result = demoClient.hello();
        log.error(result);
        throw new RuntimeException("error: " + result);
    }

    @GetMapping("/user/{name}")
    public String user(@PathVariable("name") String name) {
        return "get /user/<b>" + name + "</b>, response: " + Feign.builder()
                .requestInterceptor(template -> template.header("end-user", name))
                .target(DemoClient.class, url).hello();
    }
}
