package com.trendyol.linkconverter.services.converter.search;

import com.trendyol.linkconverter.services.converter.BaseWebLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

@Component
public class WebLinkSearchConverter extends BaseWebLinkConverter {

    @Override
    protected LinkType outputLinkType() {
        return LinkType.WEB_URL;
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
