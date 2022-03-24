package com.trendyol.linkconverter.services.validation;

import java.util.concurrent.ExecutionException;

/**
 * The custom exception which used in workaround for cache which used in
 * {@link com.trendyol.linkconverter.services.LinkConverterProcessManager}
 * {@see} the description of private method <b>LinkConverterProcessManager#getLinkDTOWithUsingCache</b>
 */
public class EntityIsMissingInDBException extends ExecutionException {
}
