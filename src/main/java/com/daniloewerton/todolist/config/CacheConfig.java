package com.daniloewerton.todolist.config;

import com.daniloewerton.todolist.infra.cache.CustomCacheErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig implements CachingConfigurer {

    public static final String CACHE_MANAGER = "cacheManager";

    public static final String CACHE_TASK = "cacheManagerTask";

    public static final String CACHE_USER = "cacheManagerUser";

    @Value("${spring.redis.expiration-minutes}")
    private long expirationMinutes;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Bean
    public CacheManager cacheManager(final RedisConnectionFactory redisConnectionFactory) {
        log.info("Creating Redis Cache Manager {}, host: {}, port: {}", CACHE_MANAGER, host, port);
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
                .withInitialCacheConfigurations(cacheConfigurations())
                .build();
    }

    private Map<String, RedisCacheConfiguration> cacheConfigurations() {
        final Map<String, RedisCacheConfiguration> configurations = new HashMap<>();
        configurations.put(CACHE_MANAGER, cacheConfiguration(expirationMinutes));
        configurations.put(CACHE_TASK, cacheConfiguration(expirationMinutes));
        configurations.put(CACHE_USER, cacheConfiguration(expirationMinutes));
        return configurations;
    }

    public RedisCacheConfiguration cacheConfiguration(final Long expirationMinutes) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(expirationMinutes))
                .disableCachingNullValues();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CustomCacheErrorHandler();
    }
}