package com.xhh.demo.lettuce.controller;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * com.xhh.demo.lettuce.controller
 *
 * @author tiger
 */
@Log4j2
@RestController
public class DemoController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/save/{value}")
    public String save(@PathVariable String value) {
        redisTemplate.opsForValue().set("demo", value, 60, TimeUnit.SECONDS);
        return "save success";
    }

    @PostMapping("/s/{value}")
    public String s(@PathVariable String a) throws InterruptedException {
        RedisURI uri = RedisURI.builder().withHost("192.168.56.200").withPort(7001).build();
        RedisClusterClient redisClusterClient = RedisClusterClient.create(uri);
        ClusterTopologyRefreshOptions options = ClusterTopologyRefreshOptions.builder()
                .enableAdaptiveRefreshTrigger(
                        ClusterTopologyRefreshOptions.RefreshTrigger.MOVED_REDIRECT,
                        ClusterTopologyRefreshOptions.RefreshTrigger.PERSISTENT_RECONNECTS
                )
                .adaptiveRefreshTriggersTimeout(Duration.of(30, ChronoUnit.SECONDS))
                .build();
        redisClusterClient.setOptions(ClusterClientOptions.builder().topologyRefreshOptions(options).build());
        StatefulRedisClusterConnection<String, String> connection = redisClusterClient.connect();
        RedisAdvancedClusterCommands<String, String> commands = connection.sync();
        commands.setex("name", 10, "throwable");
        String value = commands.get("name");
        log.info("Get value:{}", value);
        Thread.sleep(Integer.MAX_VALUE);
        connection.close();
        redisClusterClient.shutdown();

        return "save success";
    }
}
