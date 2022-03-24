package com.trendyol.linkconverter.services.converter.search;

import com.trendyol.linkconverter.services.converter.BaseWebLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

import static com.trendyol.linkconverter.services.utils.QueryConstants.APP_LINK_PARAMETER_QUERY;
import static com.trendyol.linkconverter.services.utils.QueryConstants.WEB_LINK_PARAMETER_QUERY;

/**
 * In this class made overriding the basic methods for <b>web link</b> building which is used in abstract class
 * {@link com.trendyol.linkconverter.services.converter.BaseLinkConverter}.
 * the description of overriding methods see in BaseLinkConverter class.
 */
@Component
public class WebLinkSearchConverter extends BaseWebLinkConverter {
    protected static final String WEB_LINK_PATH_SEARCH = "sr";

    @Override
    protected LinkType outputLinkType() {
        return LinkType.WEB_LINK;
    }

    @Override
    protected MultiValueMap<String, String> queryParameters(final String link) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Optional.ofNullable(getQueryParams(link).get(APP_LINK_PARAMETER_QUERY))
                .ifPresent(value -> params.add(WEB_LINK_PARAMETER_QUERY, value));
        return params;
    }

    @Override
    protected String path(String link) {
        return WEB_LINK_PATH_SEARCH;
    }
}
