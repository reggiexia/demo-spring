package com.xhh.demo.lettuce.config;

import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * redis 配置
 *
 * @author tiger
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisCacheConfiguration {

    private final RedisProperties redisProperties;

    public RedisCacheConfiguration(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    /**
     * 为 RedisTemplate 设置 redis client 连接实现
     */
    @Bean
    public RedisTemplate<String, Serializable> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    /**
     * 配置 LettuceConnectionFactory
     */
    @Bean(destroyMethod = "destroy")
    public LettuceConnectionFactory lettuceConnectionFactoryUvPv() {
        // 处理 spring.redis.cluster.nodes 的值
        List<String> clusterNodes = redisProperties.getCluster().getNodes();
        Set<RedisNode> nodes = new HashSet<>();
        clusterNodes.forEach(address -> {
            String[] hosts = address.split(":");
            nodes.add(new RedisNode(hosts[0].trim(),
                Integer.parseInt(hosts[1])));
        });
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
        clusterConfiguration.setClusterNodes(nodes);
        //clusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        //clusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());

        // 设置连接池
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        poolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
        poolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());

        ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                // 开启所有自适应刷新，MOVED，ASK，PERSISTENT都会触发
                .enableAllAdaptiveRefreshTriggers()
                // 自适应刷新超时时间(默认30秒), 该参数用来控制刷新频率，超时时间之内多次触发刷新只有第一个触发器能刷新成功
                .adaptiveRefreshTriggersTimeout(Duration.ofSeconds(2))
                .build();

        LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .clientOptions(ClusterClientOptions.builder().topologyRefreshOptions(topologyRefreshOptions)
                        .timeoutOptions(TimeoutOptions.enabled(redisProperties.getTimeout())).build())
                // 将appID传入连接，方便Redis监控中查看
                //.clientName(appName + "_lettuce")
                .build();

        return new LettuceConnectionFactory(clusterConfiguration, lettuceClientConfiguration);
    }
}
