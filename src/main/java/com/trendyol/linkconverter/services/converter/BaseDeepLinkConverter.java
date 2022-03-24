package com.trendyol.linkconverter.services.converter;

import org.springframework.beans.factory.annotation.Value;

import static com.trendyol.linkconverter.services.utils.QueryConstants.EMPTY_STR;

/**
 * Base abstract class which override the general methods for <b>deeplink</b> converting from {@link BaseLinkConverter}
 */
public abstract class BaseDeepLinkConverter extends BaseLinkConverter {
    @Value("${app.link.deeplink.scheme}")
    private String scheme;

    @Override
    protected String scheme() {
        return scheme;
    }

    @Override
    protected String host() {
        return EMPTY_STR;
    }

    @Override
    protected String path(String link) {
        return EMPTY_STR;
    }
}
