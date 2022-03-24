package com.trendyol.linkconverter.services.converter;

public abstract class BaseWebLinkConverter extends BaseLinkConverter {

    @Override
    protected String scheme() {
        return BASE_WEB_SCHEME;
    }

    @Override
    protected String host() {
        return BASE_WEB_HOST;
    }

    @Override
    protected String path(String link) {
        return EMPTY_STR;
    }
}
