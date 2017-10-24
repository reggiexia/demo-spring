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
public class WarnGaugeMetric {

    public static final Gauge requests = Gauge.build()
            .name("warn_or_not")
            .help("Warn or not")
            .labelNames("method")
            .register();

    /**
     * 告警值提示
     *
     * @param i 1：告警，0：不告警
     */
    public void processRequest(double i){
        requests.labels("warn").set(i);
    }
}
