package com.trendyol.linkconverter.config;


import com.trendyol.linkconverter.db.service.impl.LinksStorageServiceImpl;
import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.services.cache.LoadingCacheStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.TimeUnit;

/**
 * Configuration for creating new bean types of caches
 */
@Configuration
@PropertySource(value = "classpath:cache.properties")
public class CacheProviderConfiguration {


    @Bean
    public LoadingCacheStore<LinkDTO, LinkDTO> linksStorageCache(@Value("${cache.links_storage.duration.expire_time}") Integer expireTime,
                                                                 @Value("${cache.links_storage.duration.time_unit}") TimeUnit timeUnit,
                                                                 @Value("${cache.links_storage.max_size}") Integer maxSize,
                                                                 LinksStorageServiceImpl cacheService) {
        return new LoadingCacheStore<>(expireTime, timeUnit, maxSize, cacheService);
    }
}
