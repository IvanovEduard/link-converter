package com.trendyol.linkconverter.services.converter;

public abstract class BaseDeepLinkConverter extends BaseLinkConverter {

    @Override
    protected String scheme() {
        return BASE_DEEPLINK_SCHEME;
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
