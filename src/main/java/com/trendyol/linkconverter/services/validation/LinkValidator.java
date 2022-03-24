package com.trendyol.linkconverter.services.validation;

import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implementation the base constraints for <b>link</b> validation
 * which applied by the {@link ValidateLink}
 * in the next entities:
 * {@link com.trendyol.linkconverter.dto.DeeplinkToConvertDTO}
 * {@link com.trendyol.linkconverter.dto.WebLinkToConvertDTO}
 */
public class LinkValidator implements ConstraintValidator<ValidateLink, String> {
    private String host;
    private String protocol;

    @Override
    public void initialize(ValidateLink validateLink) {
        this.host = validateLink.host();
        this.protocol = validateLink.protocol();
    }

    /**
     * Implementation the base constraints for <b>link</b> validation.
     *
     * @param link                       input link for validation
     * @param constraintValidatorContext context which i snot used in the method implementation
     * @return result of validation
     */
    @Override
    public boolean isValid(String link, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.hasLength(link)) {
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(link).build();
            String host = uriComponents.getHost();
            String scheme = uriComponents.getScheme();
            return this.protocol.equals(scheme) && this.host.equals(host);
        }
        return true;
    }
}
