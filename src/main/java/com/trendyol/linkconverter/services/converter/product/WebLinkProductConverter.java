package com.trendyol.linkconverter.services.converter.product;

import com.trendyol.linkconverter.services.converter.BaseLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.StringJoiner;

import static java.util.Objects.nonNull;

@Component
public class WebLinkProductConverter extends BaseLinkConverter {

    @Override
    protected String buildBaseLink(final String link) {
        return BASE_WEB_URL +
                SYMBOL_SLASH +
                WEB_LINK_PATH_BRAND +
                SYMBOL_SLASH +
                WEB_LINK_PATH_CONTENT_ID +
                getQueryParams(link).get(APP_LINK_PARAMETER_CONTENT_ID);
    }

    @Override
    protected String buildLinkParameters(final String link) {
        Map<String, String> queryParams = getQueryParams(link);
        StringJoiner stringJoiner = new StringJoiner(SYMBOL_AMPERSAND);
        if (CollectionUtils.isEmpty(queryParams)) {
            return EMPTY_STR;
        }
        if (nonNull(queryParams.get(APP_LINK_PARAMETER_CAMPAIGN_ID))) {
            stringJoiner.add(buildParamEqual(WEB_LINK_PARAMETER_CAMPAIGN_ID, queryParams.get(APP_LINK_PARAMETER_CAMPAIGN_ID)));
        }
        if (nonNull(queryParams.get(APP_LINK_PARAMETER_MERCHANT_ID))) {
            stringJoiner.add(buildParamEqual(WEB_LINK_PARAMETER_MERCHANT_ID, queryParams.get(APP_LINK_PARAMETER_MERCHANT_ID)));
        }
        return SYMBOL_QUESTION_MARK + stringJoiner;
    }

    @Override
    protected LinkType outputLinkType() {
        return LinkType.WEB_URL;
    }
}
