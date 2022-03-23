package com.trendyol.linkconverter.services.converter.home;

import com.trendyol.linkconverter.services.converter.BaseLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import com.trendyol.linkconverter.types.PageType;
import org.springframework.stereotype.Component;

@Component
public class DeepLinkHomeConverter extends BaseLinkConverter {

    @Override
    protected LinkType outputLinkType() {
        return LinkType.DEEP_LINK;
    }

    @Override
    protected String buildBaseLink(String link) {
        return BASE_DEEPLINK;
    }

    @Override
    protected String buildLinkParameters(String link) {
        return SYMBOL_QUESTION_MARK + buildParamEqual(APP_LINK_PARAMETER_PAGE, PageType.HOME.getAppValue());
    }
}
