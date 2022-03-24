package com.trendyol.linkconverter.services.converter.home;

import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.types.LinkType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DeepLinkHomeConverterTest {
    private final DeepLinkHomeConverter deepLinkHomeConverter = new DeepLinkHomeConverter();

    @Test
    public void testOutputLinkType() {
        assertThat(deepLinkHomeConverter.outputLinkType(), is(LinkType.DEEP_LINK));
    }

    @Test
    public void testConvert() {
        assertThat(deepLinkHomeConverter.convert("https://www.trendyol.com/Hesabim/Favoriler"), is(LinkDTO.of("ty://?Page=Home", LinkType.DEEP_LINK)));
    }
}