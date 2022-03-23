package com.trendyol.linkconverter.services.router;

import com.trendyol.linkconverter.services.converter.BaseLinkConverter;
import com.trendyol.linkconverter.services.converter.search.DeepLinkSearchConverter;
import com.trendyol.linkconverter.services.converter.search.WebLinkSearchConverter;
import com.trendyol.linkconverter.types.PageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchLinkConvertExecutor implements LinkConvertExecutor {
    private final DeepLinkSearchConverter deepLinkSearchConverter;
    private final WebLinkSearchConverter webLinkSearchConverter;

    @Override
    public BaseLinkConverter toDeepLinkConverter() {
        return deepLinkSearchConverter;
    }

    @Override
    public BaseLinkConverter toWebLinkConverter() {
        return webLinkSearchConverter;
    }

    @Override
    public PageType getPageType() {
        return PageType.SEARCH;
    }
}
