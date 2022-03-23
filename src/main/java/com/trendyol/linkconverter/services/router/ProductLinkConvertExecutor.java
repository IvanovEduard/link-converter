package com.trendyol.linkconverter.services.router;

import com.trendyol.linkconverter.services.converter.BaseLinkConverter;
import com.trendyol.linkconverter.services.converter.product.DeepLinkProductConverter;
import com.trendyol.linkconverter.services.converter.product.WebLinkProductConverter;
import com.trendyol.linkconverter.types.PageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductLinkConvertExecutor implements LinkConvertExecutor {
    private final DeepLinkProductConverter deepLinkProductConverter;
    private final WebLinkProductConverter webLinkProductConverter;

    @Override
    public BaseLinkConverter toDeepLinkConverter() {
        return deepLinkProductConverter;
    }

    @Override
    public BaseLinkConverter toWebLinkConverter() {
        return webLinkProductConverter;
    }

    @Override
    public PageType getPageType() {
        return PageType.PRODUCT;
    }
}
