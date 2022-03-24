package com.trendyol.linkconverter.services.converter.search;

import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.types.LinkType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.trendyol.linkconverter.services.converter.BaseLinkConverter.WEB_LINK_PARAMETER_QUERY;
import static com.trendyol.linkconverter.services.converter.BaseLinkConverter.WEB_LINK_PATH_SEARCH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WebLinkSearchConverterTest {
    private final WebLinkSearchConverter webLinkSearchConverter = new WebLinkSearchConverter();

    private static final String DEEP_LINK = "ty://?Page=Search&Query=çocuk%20yemeği";

    @Test
    public void testOutputLinkType() {
        assertThat(webLinkSearchConverter.outputLinkType(), is(LinkType.WEB_URL));
    }

    @Test
    public void testBuildPath() {
        assertThat(webLinkSearchConverter.path(DEEP_LINK), is(WEB_LINK_PATH_SEARCH));
    }

    @Test
    public void testBuildLinkParameters() {
        assertThat(webLinkSearchConverter.queryParameters(DEEP_LINK).toSingleValueMap(), is(Map.of(WEB_LINK_PARAMETER_QUERY, "çocuk%20yemeği")));
    }

    @Test
    public void testConvert() {
        assertThat(webLinkSearchConverter.convert(DEEP_LINK), is(LinkDTO.of("https://www.trendyol.com/sr?q=çocuk%20yemeği", LinkType.WEB_URL)));
    }

}