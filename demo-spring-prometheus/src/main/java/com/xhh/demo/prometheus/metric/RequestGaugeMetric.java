package com.xhh.demo.prometheus.metric;

import io.prometheus.client.Gauge;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * prometheus gauge 示例
 *
 * gauge 值可以累加，可以减小
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2017/10/11 下午3:17
 */
@Component
public class RequestGaugeMetric {

    private static final Gauge requests = Gauge.build()
            .name("requests_int")
            .help("Request int value")
            .labelNames("method")
            .register();

    public void processRequest(String method){
        Random random = new Random();
        int value = random.nextInt(100) + 1;
        requests.labels(method.toUpperCase()).set(value);
    }
}
