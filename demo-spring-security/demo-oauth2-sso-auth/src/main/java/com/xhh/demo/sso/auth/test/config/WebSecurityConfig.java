package com.xhh.demo.sso.auth.test.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;

/**
 * WebSecurity 配置
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2017/4/20 上午9:47
 */
@Profile("test")
@Log4j2
@RestController
public class WebSecurityConfig {

    @Profile("test")
    @Configuration
    static class MvcConfig extends WebMvcConfigurerAdapter {

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("login").setViewName("login");
            registry.addViewController("/").setViewName("index");
        }
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @PreAuthorize("authenticated and hasPermission('read', 'view')")
    public ModelAndView hello(HttpServletRequest request, ModelAndView modelAndView) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String username = request.getRemoteUser();
        modelAndView.setViewName("hello");
        modelAndView.addObject("message", username);
        return modelAndView;
    }

}
