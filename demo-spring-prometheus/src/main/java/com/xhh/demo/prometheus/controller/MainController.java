package com.xhh.demo.prometheus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * main controller
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2017/9/26 下午4:55
 */
@RestController
public class MainController {

    @RequestMapping("/")
    public String home() {
        return "Hello HOME";
    }

    @RequestMapping("/message")
    public String message() {
        return "Hello INFO";
    }
}
