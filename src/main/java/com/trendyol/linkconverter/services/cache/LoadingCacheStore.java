package com.trendyol.linkconverter.services.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.trendyol.linkconverter.services.validation.EntityIsMissingInDBException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;

/**
 * Class for cache instantiate.
 * The all new initializations should to placed in bean creation in {@link com.trendyol.linkconverter.config.CacheProviderConfiguration}
 *
 * @param <K> key by which the value should be fetched from cache
 * @param <V> value which saved in the cache
 */
@Slf4j
public class LoadingCacheStore<K, V> {
    private LoadingCache<K, V> loadingCache;

    /**
     * Initializing cache according to the input parameters.
     *
     * @param expireTime   the length of time after an entry is created that it should be automatically removed
     * @param timeUnit     the unit that duration is expressed in
     * @param maxSize      the maximum size of the cache
     * @param cacheService service which can return the data
     */
    public LoadingCacheStore(int expireTime, TimeUnit timeUnit, int maxSize, CacheService<K, V> cacheService) {
        CacheLoader<K, V> cacheLoader = new CacheLoader<>() {
            @Override
            public V load(@NonNull K key) throws Exception {
                V value = cacheService.getData(key);
                if (nonNull(value)) {
                    return value;
                }
                throw new EntityIsMissingInDBException();
            }
        };

        loadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(expireTime, timeUnit)
                .maximumSize(maxSize)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build(cacheLoader);
    }

    /**
     * Fetch data from cache.
     * In case when the result is null we throw the custom {@link EntityIsMissingInDBException} exception
     * for avoiding hold in cache the empty <b>Optional</b> element.
     * In case some unexpected error during data fetching we throw the exception from this method
     * which should be handled in {@link com.trendyol.linkconverter.controllers.ErrorHandlerController#handleApplicationException(Exception, HandlerMethod)}
     *
     * @param key value by which the value should be fetched from cache
     * @return value which saved in the cache
     * @throws ExecutionException in case when the result is null we throw
     *                            the custom {@link EntityIsMissingInDBException} exception, look on the ctor of {@link LoadingCacheStore}
     */
    public V get(K key) throws ExecutionException {
        try {
            return loadingCache.get(key);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof EntityIsMissingInDBException) {
                return null;
            }
            log.error("Error occurs during load data by cache", e);
            throw e;
        }
    }
}
