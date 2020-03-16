package com.xhh.demo.prometheus.controller;

import com.xhh.demo.prometheus.metric.WarnGaugeMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * main controller
 *
 * @author tiger
 * @version 1.0.0 createTime: 2017/9/26 下午4:55
 */
@RestController
public class MainController {

    @Autowired
    private WarnGaugeMetric warnGaugeMetric;

    @RequestMapping("/")
    public String home() {
        return "Hello HOME";
    }

    @RequestMapping("/message")
    public String message() {
        return "Hello INFO";
    }

    @RequestMapping("/warn/{num}")
    public String warn(@PathVariable("num") double num) {
        warnGaugeMetric.processRequest(num);
        return "warn";
    }
}
