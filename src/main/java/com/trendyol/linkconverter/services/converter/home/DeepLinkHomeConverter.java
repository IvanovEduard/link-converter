package com.trendyol.linkconverter.services.converter.home;

import com.trendyol.linkconverter.services.converter.BaseDeepLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import com.trendyol.linkconverter.types.PageType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
