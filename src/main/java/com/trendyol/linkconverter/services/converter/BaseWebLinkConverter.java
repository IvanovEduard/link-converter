package com.trendyol.linkconverter.services.converter;

import org.springframework.beans.factory.annotation.Value;

import static com.trendyol.linkconverter.services.utils.QueryConstants.EMPTY_STR;

/**
 * Base abstract class which override the general methods for <b>web link</b> converting from {@link BaseLinkConverter}
 */
public abstract class BaseWebLinkConverter extends BaseLinkConverter {
    @Value("${app.link.web.scheme}")
    private String scheme;
    @Value("${app.link.web.host}")
    private String host;

    @Override
    protected String scheme() {
        return scheme;
    }

    @Override
    protected String host() {
        return host;
    }

    @Override
    protected String path(String link) {
        return EMPTY_STR;
    }
}
