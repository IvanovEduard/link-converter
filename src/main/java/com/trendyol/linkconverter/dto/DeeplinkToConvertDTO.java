package com.trendyol.linkconverter.dto;

import com.trendyol.linkconverter.services.validation.ValidateLink;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static com.trendyol.linkconverter.services.validation.ValidationErrorMessage.ERROR_LINK_SHOULD_BE_PRESENT;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeeplinkToConvertDTO {
    @NotBlank(message = ERROR_LINK_SHOULD_BE_PRESENT)
    @ValidateLink(protocol = "ty")
    String link;
}
