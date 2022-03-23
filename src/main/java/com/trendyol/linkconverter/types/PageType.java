package com.trendyol.linkconverter.types;

import lombok.Getter;

@Getter
public enum PageType {
    PRODUCT("-p-", "Product"),
    SEARCH("sr?", "Search"),
    HOME(null, "Home");

    private final String webValue;
    private final String appValue;

    PageType(String webValue, String appValue) {
        this.webValue = webValue;
        this.appValue = appValue;
    }
}
