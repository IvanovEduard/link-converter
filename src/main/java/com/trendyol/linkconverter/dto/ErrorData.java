package com.trendyol.linkconverter.dto;

import com.trendyol.linkconverter.types.ErrorType;
import lombok.Value;

@Value(staticConstructor = "of")
public class ErrorData {
    String errorMessage;
    ErrorType errorType;
}
