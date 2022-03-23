package com.trendyol.linkconverter.services.validation;

import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LinkValidator implements ConstraintValidator<ValidateLink, String> {
    private String host;
    private String protocol;

    @Override
    public void initialize(ValidateLink validateLink) {
        this.host = validateLink.host();
        this.protocol = validateLink.protocol();
    }

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
