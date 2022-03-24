package com.trendyol.linkconverter.dto;

import com.trendyol.linkconverter.services.validation.ValidateLink;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static com.trendyol.linkconverter.services.validation.ValidationErrorMessage.ERROR_LINK_SHOULD_BE_PRESENT;

/**
 * Data transfer object for convert <b>web link</b> to <b>deepLink</b>
 * {@link com.trendyol.linkconverter.controllers.LinkConverterController#convertToDeeplink(WebLinkToConvertDTO)}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebLinkToConvertDTO {
    @NotBlank(message = ERROR_LINK_SHOULD_BE_PRESENT)
    @ValidateLink(protocol = "https", host = "www.trendyol.com")
    String link;
}
