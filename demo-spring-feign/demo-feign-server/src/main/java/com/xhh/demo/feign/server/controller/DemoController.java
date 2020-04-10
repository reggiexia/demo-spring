package com.xhh.demo.feign.server.controller;

import com.xhh.demo.feign.server.exception.BusinessError;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Log4j2
@RestController
public class DemoController {

    @Value("${api.version}")
    private String version;

    @GetMapping("/span")
    public String span() {
        log.info("Span was called by client");
        return "this is child";
    }

    @PostMapping("/demo")
    public String demo(HttpServletRequest request, @RequestBody Foo foo) {
        log.info("PARAM name: {}", foo.getName());
        Enumeration<String> names = request.getHeaderNames();
        StringBuilder headers = new StringBuilder("headers: ");
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            headers.append(name).append(":").append(request.getHeader(name)).append("; ");
        }
        return "{\"code\":\"000000\", \"message\":\"SUCCESS\", \"name\": \"" + foo.getName() + "\", \"headers\": \"" +
                headers + "\"}";
    }

    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        String user = request.getHeader("end-user");
        if (StringUtils.hasText(user)) {
            log.info("end-user: {}", user);
        }
        return "<b>" + version + "</b> Hello Istio!";
    }

    @GetMapping("/api/exceptions/throwing/annotated")
    public String throwingAnnotatedException() {
        throw new CustomException("Bad Custom request");
    }

    @GetMapping("/api/exceptions/throwing/common")
    public String throwingCommonException() {
        throw new RuntimeException("a common runtime exception");
    }
}

@BusinessError(status=400)
class CustomException extends  RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
