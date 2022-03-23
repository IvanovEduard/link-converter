package com.trendyol.linkconverter.services.converter.search;

import com.trendyol.linkconverter.services.converter.BaseLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WebLinkSearchConverter extends BaseLinkConverter {

    @Override
    protected LinkType outputLinkType() {
        return LinkType.WEB_URL;
    }

    @Override
    protected String buildBaseLink(String link) {
        return BASE_WEB_URL + SYMBOL_SLASH + SYMBOL_QUESTION_MARK;
    }

    @Override
    protected String buildLinkParameters(final String link) {
        Map<String, String> queryParams = getQueryParams(link);
        return buildParamEqual(WEB_LINK_PARAMETER_QUERY, queryParams.get(APP_LINK_PARAMETER_QUERY));
    }
}
