package com.trendyol.linkconverter.services.executor;

import com.trendyol.linkconverter.services.converter.BaseLinkConverter;
import com.trendyol.linkconverter.services.converter.home.DeepLinkHomeConverter;
import com.trendyol.linkconverter.services.converter.home.WebLinkHomeConverter;
import com.trendyol.linkconverter.types.PageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Contains convertors {@link DeepLinkHomeConverter} {@link WebLinkHomeConverter} for links of type {@link PageType#HOME}
 */
@Component
@RequiredArgsConstructor
public class HomeLinkConvertExecutor implements LinkConvertExecutor {
    private final DeepLinkHomeConverter deepLinkHomeConverter;
    private final WebLinkHomeConverter webLinkHomeConverter;

    @Override
    public BaseLinkConverter toDeepLinkConverter() {
        return deepLinkHomeConverter;
    }

    @Override
    public BaseLinkConverter toWebLinkConverter() {
        return webLinkHomeConverter;
    }

    @Override
    public PageType getPageType() {
        return PageType.HOME;
    }
}
