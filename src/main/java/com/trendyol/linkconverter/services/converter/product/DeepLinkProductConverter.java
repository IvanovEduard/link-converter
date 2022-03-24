package com.trendyol.linkconverter.services.converter.product;

import com.trendyol.linkconverter.services.converter.BaseDeepLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import com.trendyol.linkconverter.types.PageType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DeepLinkProductConverter extends BaseDeepLinkConverter {
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
    protected MultiValueMap<String, String> queryParameters(final String link) {
        Map<String, String> webLinkQueryParams = getQueryParams(link);
        MultiValueMap<String, String> multiValuedMap = new LinkedMultiValueMap<>();
        multiValuedMap.add(APP_LINK_PARAMETER_PAGE, PageType.PRODUCT.getAppValue());
        getContentId(link).ifPresent(id -> multiValuedMap.add(APP_LINK_PARAMETER_CONTENT_ID, id));
        Optional.ofNullable(webLinkQueryParams.get(WEB_LINK_PARAMETER_CAMPAIGN_ID))
                .ifPresent(value -> multiValuedMap.add(APP_LINK_PARAMETER_CAMPAIGN_ID, value));
        Optional.ofNullable(webLinkQueryParams.get(WEB_LINK_PARAMETER_MERCHANT_ID))
                .ifPresent(value -> multiValuedMap.add(APP_LINK_PARAMETER_MERCHANT_ID, value));
        return multiValuedMap;
    }
}
