package com.trendyol.linkconverter.services.converter.product;

import com.trendyol.linkconverter.services.converter.BaseWebLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.Optional;

import static com.trendyol.linkconverter.services.utils.QueryConstants.*;

/**
 * In this class made overriding the basic methods for <b>web link</b> building which is used in abstract class
 * {@link com.trendyol.linkconverter.services.converter.BaseLinkConverter}.
 * the description of overriding methods see in BaseLinkConverter class.
 */
@Component
public class WebLinkProductConverter extends BaseWebLinkConverter {
    private static final String WEB_LINK_PATH_BRAND = "brand";
    private static final String WEB_LINK_PATH_CONTENT_ID = "name-p-";
    private static final String SYMBOL_SLASH = "/";

    @Override
    protected MultiValueMap<String, String> queryParameters(final String link) {
        Map<String, String> deepLinkQueryParams = getQueryParams(link);
        if (CollectionUtils.isEmpty(deepLinkQueryParams)) {
            return new LinkedMultiValueMap<>();
        }
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Optional.ofNullable(deepLinkQueryParams.get(APP_LINK_PARAMETER_CAMPAIGN_ID))
                .ifPresent(value -> params.add(WEB_LINK_PARAMETER_CAMPAIGN_ID, value));
        Optional.ofNullable(deepLinkQueryParams.get(APP_LINK_PARAMETER_MERCHANT_ID))
                .ifPresent(value -> params.add(WEB_LINK_PARAMETER_MERCHANT_ID, value));
        return params;
    }

    @Override
    protected String path(String link) {
        return WEB_LINK_PATH_BRAND +
                SYMBOL_SLASH +
                WEB_LINK_PATH_CONTENT_ID +
                getQueryParams(link).get(APP_LINK_PARAMETER_CONTENT_ID);
    }

    @Override
    protected LinkType outputLinkType() {
        return LinkType.WEB_LINK;
    }
}
