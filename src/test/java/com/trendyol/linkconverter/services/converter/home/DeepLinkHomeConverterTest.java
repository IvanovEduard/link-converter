package com.trendyol.linkconverter.services.converter.home;

import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.types.LinkType;
import com.trendyol.linkconverter.types.PageType;
import org.junit.jupiter.api.Test;

import static com.trendyol.linkconverter.services.converter.BaseLinkConverter.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DeepLinkHomeConverterTest {
    private final DeepLinkHomeConverter deepLinkHomeConverter = new DeepLinkHomeConverter();

    @Test
    public void outputLinkType() {
        assertThat(deepLinkHomeConverter.outputLinkType(), is(LinkType.DEEP_LINK));
    }

    @Test
    public void buildBaseLink() {
        assertThat(deepLinkHomeConverter.buildBaseLink(EMPTY_STR), is(BASE_DEEPLINK));
    }

    @Test
    public void buildLinkParameters() {
        assertThat(deepLinkHomeConverter.buildLinkParameters(EMPTY_STR),
                is(SYMBOL_QUESTION_MARK + APP_LINK_PARAMETER_PAGE + SYMBOL_EQUAL + PageType.HOME.getAppValue()));
    }

    @Test
    public void testConvert() {
        assertThat(deepLinkHomeConverter.convert("https://www.trendyol.com/Hesabim/Favoriler"), is(LinkDTO.of("ty://?Page=Home", LinkType.DEEP_LINK)));
    }
}