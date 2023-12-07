package com.daniloewerton.todolist.infra.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

@Slf4j
public class CustomCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(final RuntimeException exception, final Cache cache, final Object o) {
        log.error(exception.getMessage(), exception);
    }

    @Override
    public void handleCachePutError(final RuntimeException exception, final Cache cache, final Object o, final Object o1) {
        log.error(exception.getMessage(), exception);
    }

    @Override
    public void handleCacheEvictError(final RuntimeException exception, final Cache cache, final Object o) {
        log.error(exception.getMessage(), exception);
    }

    @Override
    public void handleCacheClearError(final RuntimeException exception, final Cache cache) {
        log.error(exception.getMessage(), exception);
    }
}