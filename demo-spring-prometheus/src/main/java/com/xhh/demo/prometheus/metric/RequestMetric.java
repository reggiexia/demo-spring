package com.xhh.demo.prometheus.metric;

import io.prometheus.client.Counter;
import org.springframework.stereotype.Component;

/**
 * 请求指标
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2017/9/26 下午4:50
 */
@Component
public class RequestMetric {

    public static final Counter requests = Counter.build()
            .name("requests_total")
            .help("Total requests.")
            .labelNames("method")
            .register();

    public void processRequest(String method){
        requests.labels(method.toUpperCase()).inc();
    }
}
