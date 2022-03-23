package com.trendyol.linkconverter.services.utils;

import com.trendyol.linkconverter.types.PageType;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class PageTypeDetectorTest {
    private final static String WEB_LINK_PRODUCT = "https://www.trendyol.com/casio/saat-p-1925865?boutiqueId=439892&merchantId=105064";
    private final static String WEB_LINK_SEARCH = "https://www.trendyol.com/sr?q=elbise";
    private final static String WEB_LINK_HOME = "https://www.trendyol.com/Hesabim/Favoriler";
    private final static String DEEP_LINK_PRODUCT = "https://www.trendyol.com/brand/name-p-1925865?boutiqueId=439892&merchantId=1050";
    private final static String DEEP_LINK_SEARCH = "ty://?Page=Search&Query=elbise";
    private final static String DEEP_LINK_HOME = "ty://?Page=Home";

    @Test
    public void testDetectProductPageTypeForWebLink() {
        assertThat(PageTypeDetector.detectPageType(WEB_LINK_PRODUCT), is(PageType.PRODUCT));
    }

    @Test
    public void testDetectProductPageTypeForDeepLink() {
        assertThat(PageTypeDetector.detectPageType(DEEP_LINK_PRODUCT), is(PageType.PRODUCT));
    }

    @Test
    public void testDetectSearchPageTypeForWebLink() {
        assertThat(PageTypeDetector.detectPageType(WEB_LINK_SEARCH), is(PageType.SEARCH));
    }

    @Test
    public void testDetectSearchPageTypeForDeepLink() {
        assertThat(PageTypeDetector.detectPageType(DEEP_LINK_SEARCH), is(PageType.SEARCH));
    }

    @Test
    public void testDetectHomePageTypeForWebLink() {
        assertThat(PageTypeDetector.detectPageType(WEB_LINK_HOME), is(PageType.HOME));
    }

    @Test
    public void testDetectHomePageTypeForDeepLink() {
        assertThat(PageTypeDetector.detectPageType(DEEP_LINK_HOME), is(PageType.HOME));
    }
}