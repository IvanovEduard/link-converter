package com.trendyol.linkconverter.services.utils;

import com.trendyol.linkconverter.types.PageType;
import lombok.experimental.UtilityClass;


@UtilityClass
public class PageTypeDetector {

    public static PageType detectPageType(final String link) {
        if (link.contains(PageType.SEARCH.getWebValue()) || link.contains(PageType.SEARCH.getAppValue())) {
            return PageType.SEARCH;
        }
        if (link.contains(PageType.PRODUCT.getWebValue()) || link.contains(PageType.PRODUCT.getAppValue())) {
            return PageType.PRODUCT;
        }
        return PageType.HOME;
    }
}
