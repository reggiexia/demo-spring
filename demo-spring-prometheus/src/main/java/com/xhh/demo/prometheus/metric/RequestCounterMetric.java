package com.xhh.demo.prometheus.metric;

import io.prometheus.client.Counter;
import org.springframework.stereotype.Component;

/**
 * prometheus counter 示例
 *
 * counter 计数器，只累加
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2017/10/11 下午2:35
 */
@Component
public class RequestCounterMetric {

    public static final Counter requests = Counter.build()
            .name("requests_total")
            .help("Total requests.")
            .labelNames("method", "aaa")
            .register();

    public void processRequest(String method){
        requests.labels(method.toUpperCase(), "bbb").inc();
    }
}
