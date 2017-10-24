package com.xhh.demo.prometheus.interceptor;

import com.xhh.demo.prometheus.metric.RequestCounterMetric;
import com.xhh.demo.prometheus.metric.RequestGaugeMetric;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 17/11/2016 7:34 PM
 */
@Log4j2
@Component
public class MainInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RequestCounterMetric requestCounterMetric;

    @Autowired
    private RequestGaugeMetric requestGaugeMetric;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        requestCounterMetric.processRequest("get");
        requestCounterMetric.processRequest("post");
        requestGaugeMetric.processRequest("get");
        requestGaugeMetric.processRequest("post");
        return true;
    }

}
