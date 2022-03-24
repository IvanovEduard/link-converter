package com.trendyol.linkconverter.services.converter.home;

import com.trendyol.linkconverter.services.converter.BaseWebLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * In this class made overriding the basic methods for <b>web link</b> building which is used in abstract class
 * {@link com.trendyol.linkconverter.services.converter.BaseLinkConverter}.
 * the description of overriding methods see in BaseLinkConverter class.
 */
@Component
public class WebLinkHomeConverter extends BaseWebLinkConverter {

    @Override
    protected LinkType outputLinkType() {
        return LinkType.WEB_LINK;
    }

    @Override
    protected MultiValueMap<String, String> queryParameters(String link) {
        return new LinkedMultiValueMap<>();
    }
}
