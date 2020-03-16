package com.xhh.demo.cloud.resilience4j.client;

import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * CheckService
 *
 * @author tiger
 * @version 1.0.0 createTime: 2019-12-24 17:04
 */
@Service
public class CheckService {

    public CheckService(BulkheadRegistry bulkheadRegistry,
                        ThreadPoolBulkheadRegistry threadPoolBulkheadRegistry,
                        CircuitBreakerRegistry circuitBreakerRegistry,
                        RateLimiterRegistry rateLimiterRegistry,
                        RetryRegistry retryRegistry) {
        this.bulkheadRegistry = bulkheadRegistry;
        this.threadPoolBulkheadRegistry = threadPoolBulkheadRegistry;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.rateLimiterRegistry = rateLimiterRegistry;
        this.retryRegistry = retryRegistry;
    }

    private final BulkheadRegistry bulkheadRegistry;
    private final ThreadPoolBulkheadRegistry threadPoolBulkheadRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RateLimiterRegistry rateLimiterRegistry;
    private final RetryRegistry retryRegistry;

    public Map<String, Number> check() {
        Map<String, Number> result = new HashMap<>();
        result.put("Bulkhead maxConcurrentCalls", bulkheadRegistry.getDefaultConfig().getMaxConcurrentCalls());
        result.put("CircuitBreaker failureRateThreshold", circuitBreakerRegistry.getDefaultConfig().getFailureRateThreshold());
        result.put("RateLimiter limitForPeriod", rateLimiterRegistry.getDefaultConfig().getLimitForPeriod());
        return result;
    }
}
