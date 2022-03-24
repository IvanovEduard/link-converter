package com.trendyol.linkconverter.services.converter.search;

import com.trendyol.linkconverter.services.converter.BaseDeepLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import com.trendyol.linkconverter.types.PageType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.Optional;

import static com.trendyol.linkconverter.services.utils.QueryConstants.*;

/**
 * In this class made overriding the basic methods for <b>deeplink</b> building which is used in abstract class
 * {@link com.trendyol.linkconverter.services.converter.BaseLinkConverter}.
 * the description of overriding methods see in BaseLinkConverter class.
 */
@Component
public class DeepLinkSearchConverter extends BaseDeepLinkConverter {

    @Override
    protected LinkType outputLinkType() {
        return LinkType.DEEP_LINK;
    }

    @Override
    protected MultiValueMap<String, String> queryParameters(final String link) {
        Map<String, String> webLinkQueryParams = getQueryParams(link);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(APP_LINK_PARAMETER_PAGE, PageType.SEARCH.getAppValue());
        Optional.ofNullable(webLinkQueryParams.get(WEB_LINK_PARAMETER_QUERY))
                .ifPresent(value -> params.add(APP_LINK_PARAMETER_QUERY, value));
        return params;
    }
}
