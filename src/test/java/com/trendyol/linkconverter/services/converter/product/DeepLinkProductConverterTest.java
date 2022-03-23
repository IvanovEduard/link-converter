package com.trendyol.linkconverter.services.converter.product;

import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.types.LinkType;
import org.junit.jupiter.api.Test;

import static com.trendyol.linkconverter.services.converter.BaseLinkConverter.BASE_DEEPLINK;
import static com.trendyol.linkconverter.services.converter.BaseLinkConverter.SYMBOL_QUESTION_MARK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DeepLinkProductConverterTest {
    private final DeepLinkProductConverter deepLinkProductConverter = new DeepLinkProductConverter();

    private static final String WEB_LINK = "https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?merchantId=105064";

    @Test
    public void outputLinkType() {
        assertThat(deepLinkProductConverter.outputLinkType(), is(LinkType.DEEP_LINK));
    }

    @Test
    public void buildBaseLink() {
        assertThat(deepLinkProductConverter.buildBaseLink(WEB_LINK), is(BASE_DEEPLINK + SYMBOL_QUESTION_MARK));
    }

    @Test
    public void buildLinkParameters() {
        assertThat(deepLinkProductConverter.buildLinkParameters(WEB_LINK), is("Page=Product&ContentId=1925865&MerchantId=105064"));
    }

    @Test
    public void testConvert() {
        assertThat(deepLinkProductConverter.convert(WEB_LINK), is(LinkDTO.of("ty://?Page=Product&ContentId=1925865&MerchantId=105064", LinkType.DEEP_LINK)));
    }
}