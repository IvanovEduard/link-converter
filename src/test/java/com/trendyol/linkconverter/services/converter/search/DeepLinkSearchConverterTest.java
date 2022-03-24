package com.trendyol.linkconverter.services.converter.search;

import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.types.LinkType;
import com.trendyol.linkconverter.types.PageType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.trendyol.linkconverter.services.converter.BaseLinkConverter.APP_LINK_PARAMETER_PAGE;
import static com.trendyol.linkconverter.services.converter.BaseLinkConverter.APP_LINK_PARAMETER_QUERY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DeepLinkSearchConverterTest {
    private final DeepLinkSearchConverter deepLinkSearchConverter = new DeepLinkSearchConverter();

    private static final String WEB_LINK = "https://www.trendyol.com/sr?q=çocuk%20yemeği";

    @Test
    public void testOutputLinkType() {
        assertThat(deepLinkSearchConverter.outputLinkType(), is(LinkType.DEEP_LINK));
    }

    @Test
    public void testBuildLinkParameters() {
        Map<String, String> expectedParams = Map.of(
                APP_LINK_PARAMETER_PAGE, PageType.SEARCH.getAppValue(),
                APP_LINK_PARAMETER_QUERY, "çocuk%20yemeği"
        );
        assertThat(deepLinkSearchConverter.queryParameters(WEB_LINK).toSingleValueMap(), is(expectedParams));
    }

    @Test
    public void testConvert() {
        assertThat(deepLinkSearchConverter.convert(WEB_LINK), is(LinkDTO.of("ty://?Page=Search&Query=çocuk%20yemeği", LinkType.DEEP_LINK)));
    }
}