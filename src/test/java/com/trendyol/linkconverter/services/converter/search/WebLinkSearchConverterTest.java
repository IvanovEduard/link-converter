package com.trendyol.linkconverter.services.converter.search;

import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.types.LinkType;
import org.junit.jupiter.api.Test;

import static com.trendyol.linkconverter.services.converter.BaseLinkConverter.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WebLinkSearchConverterTest {
    private final WebLinkSearchConverter webLinkSearchConverter = new WebLinkSearchConverter();

    private static final String DEEP_LINK = "ty://?Page=Search&Query=elbise";

    @Test
    public void testOutputLinkType() {
        assertThat(webLinkSearchConverter.outputLinkType(), is(LinkType.WEB_URL));
    }

    @Test
    public void testBuildBaseLink() {
        assertThat(webLinkSearchConverter.buildBaseLink(DEEP_LINK), is(BASE_WEB_URL + SYMBOL_SLASH + SYMBOL_QUESTION_MARK));
    }

    @Test
    public void testBuildLinkParameters() {
        assertThat(webLinkSearchConverter.buildLinkParameters(DEEP_LINK), is("q=elbise"));
    }

    @Test
    public void testConvert() {
        assertThat(webLinkSearchConverter.convert(DEEP_LINK), is(LinkDTO.of("https://www.trendyol.com/?q=elbise", LinkType.WEB_URL)));
    }

}