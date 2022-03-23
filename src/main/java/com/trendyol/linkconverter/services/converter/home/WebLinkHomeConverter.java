package com.trendyol.linkconverter.services.converter.home;

import com.trendyol.linkconverter.services.converter.BaseLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import org.springframework.stereotype.Component;

@Component
public class WebLinkHomeConverter extends BaseLinkConverter {

    @Override
    protected LinkType outputLinkType() {
        return LinkType.WEB_URL;
    }

    @Override
    protected String buildBaseLink(String link) {
        return BASE_WEB_URL;
    }

    @Override
    protected String buildLinkParameters(String link) {
        return EMPTY_STR;
    }
}
