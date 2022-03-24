package com.trendyol.linkconverter.dto;

import com.trendyol.linkconverter.types.ErrorType;
import lombok.Value;

/**
 * Data transfer object which used for response in {@link com.trendyol.linkconverter.controllers.ErrorHandlerController }.
 */
@Value(staticConstructor = "of")
public class ErrorDataDTO {
    String errorMessage;
    ErrorType errorType;
}
