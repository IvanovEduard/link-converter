package com.trendyol.linkconverter.services.converter.search;

import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.types.LinkType;
import org.junit.jupiter.api.Test;

import static com.trendyol.linkconverter.services.converter.BaseLinkConverter.BASE_DEEPLINK;
import static com.trendyol.linkconverter.services.converter.BaseLinkConverter.SYMBOL_QUESTION_MARK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DeepLinkSearchConverterTest {
    private final DeepLinkSearchConverter deepLinkSearchConverter = new DeepLinkSearchConverter();

    private static final String WEB_LINK = "https://www.trendyol.com/sr?q=elbise";

    @Test
    public void testOutputLinkType() {
        assertThat(deepLinkSearchConverter.outputLinkType(), is(LinkType.DEEP_LINK));
    }

    @Test
    public void testBuildBaseLink() {
        assertThat(deepLinkSearchConverter.buildBaseLink(WEB_LINK), is(BASE_DEEPLINK + SYMBOL_QUESTION_MARK));
    }

    @Test
    public void testBuildLinkParameters() {
        assertThat(deepLinkSearchConverter.buildLinkParameters(WEB_LINK), is("Page=Search&Query=elbise"));
    }

    @Test
    public void testConvert() {
        assertThat(deepLinkSearchConverter.convert(WEB_LINK), is(LinkDTO.of("ty://?Page=Search&Query=elbise", LinkType.DEEP_LINK)));
    }
}