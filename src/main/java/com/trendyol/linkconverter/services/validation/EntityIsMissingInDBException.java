package com.trendyol.linkconverter.services.validation;

import java.util.concurrent.ExecutionException;

/**
 * The custom exception which used for case when data is not present.
 * The example of using you can find in
 * {@link com.trendyol.linkconverter.services.cache.LoadingCacheStore}
 * {@see} the description for return of {@link com.google.common.cache.CacheLoader#load(Object)}
 */
public class EntityIsMissingInDBException extends ExecutionException {
}
