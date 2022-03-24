package com.trendyol.linkconverter.services.converter.home;

import com.trendyol.linkconverter.services.converter.BaseWebLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class WebLinkHomeConverter extends BaseWebLinkConverter {

    @Override
    protected LinkType outputLinkType() {
        return LinkType.WEB_URL;
    }

    @Override
    protected MultiValueMap<String, String> queryParameters(String link) {
        return new LinkedMultiValueMap<>();
    }
}
