package com.xhh.demo.cloud.resilience4j.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * CheckController
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2019-12-24 17:03
 */
@RestController
public class CheckController {

    public CheckController(CheckService checkService) {
        this.checkService = checkService;
    }

    private final CheckService checkService;


    @GetMapping("/check")
    public Map<String, Number> check() {
        return checkService.check();
    }

}