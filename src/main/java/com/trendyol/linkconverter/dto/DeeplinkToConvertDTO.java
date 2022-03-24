package com.trendyol.linkconverter.dto;

import com.trendyol.linkconverter.services.validation.ValidateLink;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static com.trendyol.linkconverter.services.validation.ValidationErrorMessage.ERROR_LINK_SHOULD_BE_PRESENT;

/**
 * Data transfer object for convert <b>deepLink</b> to <b>web link</b>
 * {@link com.trendyol.linkconverter.controllers.LinkConverterController#convertToWeb(DeeplinkToConvertDTO)}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeeplinkToConvertDTO {
    @NotBlank(message = ERROR_LINK_SHOULD_BE_PRESENT)
    @ValidateLink(protocol = "ty")
    String link;
}
