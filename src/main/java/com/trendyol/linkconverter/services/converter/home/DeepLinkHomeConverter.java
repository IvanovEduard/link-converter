package com.trendyol.linkconverter.services.converter.home;

import com.trendyol.linkconverter.services.converter.BaseDeepLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import com.trendyol.linkconverter.types.PageType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.trendyol.linkconverter.services.utils.QueryConstants.APP_LINK_PARAMETER_PAGE;

/**
 * In this class made overriding the basic methods for <b>deeplink</b> building which is used in abstract class
 * {@link com.trendyol.linkconverter.services.converter.BaseLinkConverter}.
 * the description of overriding methods see in BaseLinkConverter class.
 */
@Component
public class DeepLinkHomeConverter extends BaseDeepLinkConverter {

    @Override
    protected LinkType outputLinkType() {
        return LinkType.DEEP_LINK;
    }

    @Override
    protected MultiValueMap<String, String> queryParameters(String link) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(APP_LINK_PARAMETER_PAGE, PageType.HOME.getAppValue());
        return params;
    }
}
