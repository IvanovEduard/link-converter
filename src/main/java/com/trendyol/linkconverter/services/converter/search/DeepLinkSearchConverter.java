package com.trendyol.linkconverter.services.converter.search;

import com.trendyol.linkconverter.services.converter.BaseLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import com.trendyol.linkconverter.types.PageType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.StringJoiner;

import static java.util.Objects.nonNull;

@Component
public class DeepLinkSearchConverter extends BaseLinkConverter {

    @Override
    protected LinkType outputLinkType() {
        return LinkType.DEEP_LINK;
    }

    @Override
    protected String buildBaseLink(String link) {
        return BASE_DEEPLINK + SYMBOL_QUESTION_MARK;
    }

    @Override
    protected String buildLinkParameters(final String link) {
        Map<String, String> queryParams = getQueryParams(link);

        StringJoiner stringBuilder = new StringJoiner(SYMBOL_AMPERSAND);
        stringBuilder.add(buildParamEqual(APP_LINK_PARAMETER_PAGE, PageType.SEARCH.getAppValue()));
        if (nonNull(queryParams.get(WEB_LINK_PARAMETER_QUERY))) {
            stringBuilder.add(buildParamEqual(APP_LINK_PARAMETER_QUERY, queryParams.get(WEB_LINK_PARAMETER_QUERY)));
        }
        return stringBuilder.toString();
    }
}
