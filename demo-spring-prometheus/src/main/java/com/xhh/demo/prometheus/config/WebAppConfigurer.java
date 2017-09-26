package com.xhh.demo.prometheus.config;

import com.xhh.demo.prometheus.interceptor.MainInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 过滤器
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 17/11/2016 7:30 PM
 */
@Component
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    private MainInterceptor mainInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mainInterceptor).addPathPatterns("/**");
    }

}
