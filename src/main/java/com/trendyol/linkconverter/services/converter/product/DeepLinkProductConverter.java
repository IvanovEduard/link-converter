package com.trendyol.linkconverter.services.converter.product;

import com.trendyol.linkconverter.services.converter.BaseLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import com.trendyol.linkconverter.types.PageType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

@Component
public class DeepLinkProductConverter extends BaseLinkConverter {
    private final Pattern pattern = Pattern.compile("(-p-)(?<contentId>\\d+)");

    @Override
    protected LinkType outputLinkType() {
        return LinkType.DEEP_LINK;
    }

    private Optional<String> getContentId(final String link) {
        Matcher m = pattern.matcher(link);
        if (m.find()) {
            return Optional.of(m.group("contentId"));
        }
        return Optional.empty();
    }

    @Override
    protected String buildBaseLink(String originalLink) {
        return BASE_DEEPLINK + SYMBOL_QUESTION_MARK;
    }

    @Override
    protected String buildLinkParameters(final String link) {
        Map<String, String> queryParams = getQueryParams(link);
        StringJoiner stringJoiner = new StringJoiner(SYMBOL_AMPERSAND);
        stringJoiner.add(buildParamEqual(APP_LINK_PARAMETER_PAGE, PageType.PRODUCT.getAppValue()));
        getContentId(link).ifPresent(id -> stringJoiner.add(buildParamEqual(APP_LINK_PARAMETER_CONTENT_ID, id)));
        if (nonNull(queryParams.get(WEB_LINK_PARAMETER_CAMPAIGN_ID))) {
            stringJoiner.add(buildParamEqual(APP_LINK_PARAMETER_CAMPAIGN_ID, queryParams.get(WEB_LINK_PARAMETER_CAMPAIGN_ID)));
        }
        if (nonNull(queryParams.get(WEB_LINK_PARAMETER_MERCHANT_ID))) {
            stringJoiner.add(buildParamEqual(APP_LINK_PARAMETER_MERCHANT_ID, queryParams.get(WEB_LINK_PARAMETER_MERCHANT_ID)));
        }
        return stringJoiner.toString();
    }
}
