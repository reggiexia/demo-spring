package com.xhh.demo.jedis.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * com.xhh.demo.lettuce.controller
 *
 * @author tiger
 */
@Log4j2
@RestController
public class DemoController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * demo 存入 redis 0-5460 槽
     *
     * @param value 存入 redis 的值
     * @return 成功
     */
    @PostMapping("/save/{value}")
    public String save(@PathVariable String value) {
        String v = redisTemplate.opsForValue().get("demo");
        if (StringUtils.hasText(v)) {
            log.info("value: {}", v);
        } else {
            redisTemplate.opsForValue().set("demo", value, 300, TimeUnit.SECONDS);
        }
        return "save success";
    }
}
