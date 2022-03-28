package com.trendyol.linkconverter.services.cache;

import java.util.concurrent.ExecutionException;

/**
 * The service which will implement this interface can be used for creating new cache bean
 * in {@link com.trendyol.linkconverter.config.CacheProviderConfiguration}
 *
 * @param <K> key by which the value should be fetched from cache
 * @param <V> value which saved in the cache
 */
public interface CacheService<K, V> {
    V getData(K key) throws ExecutionException;
}
