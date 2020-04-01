package com.xhh.demo.lettuce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * com.xhh.demo.lettuce.controller
 *
 * @author tiger
 */
@RestController
public class DemoController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/save/{value}")
    public String save(@PathVariable String value) {
        redisTemplate.opsForValue().set("demo", value, 10, TimeUnit.SECONDS);
        return "save success";
    }
}
