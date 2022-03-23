package com.trendyol.linkconverter.services.converter.home;

import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.types.LinkType;
import org.junit.jupiter.api.Test;

import static com.trendyol.linkconverter.services.converter.BaseLinkConverter.BASE_WEB_URL;
import static com.trendyol.linkconverter.services.converter.BaseLinkConverter.EMPTY_STR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WebLinkHomeConverterTest {
    private final WebLinkHomeConverter webLinkHomeConverter = new WebLinkHomeConverter();

    @Test
    public void testOutputLinkType() {
        assertThat(webLinkHomeConverter.outputLinkType(), is(LinkType.WEB_URL));
    }

    @Test
    public void testBuildBaseLink() {
        assertThat(webLinkHomeConverter.buildBaseLink(EMPTY_STR), is(BASE_WEB_URL));
    }

    @Test
    public void testBuildLinkParameters() {
        assertThat(webLinkHomeConverter.buildLinkParameters(EMPTY_STR), is(EMPTY_STR));
    }

    @Test
    public void testConvert() {
        assertThat(webLinkHomeConverter.convert("ty://?Page=Favorites"), is(LinkDTO.of(BASE_WEB_URL, LinkType.WEB_URL)));
    }
}