package br.com.alanms.config;

import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheManager;
import io.quarkus.cache.CaffeineCache;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.util.Optional;

@ApplicationScoped
public class CacheConfig {

    @Inject
    CacheManager cacheManager;

    public void setExpireAfterAccess(String cacheName, Duration duration) {
        Optional<Cache> cache = cacheManager.getCache(cacheName);
        cache.ifPresent(value -> value.as(CaffeineCache.class).setExpireAfterAccess(duration));
    }
}
