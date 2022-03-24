package com.trendyol.linkconverter.services.converter.product;

import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.types.LinkType;
import com.trendyol.linkconverter.types.PageType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.trendyol.linkconverter.services.converter.BaseLinkConverter.*;
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
    public void buildLinkParameters() {
        Map<String, String> expectedResult = Map.of(
                APP_LINK_PARAMETER_PAGE, PageType.PRODUCT.getAppValue(),
                APP_LINK_PARAMETER_CONTENT_ID, "1925865",
                APP_LINK_PARAMETER_MERCHANT_ID, "105064"
        );
        assertThat(deepLinkProductConverter.queryParameters(WEB_LINK).toSingleValueMap(), is(expectedResult));
    }

    @Test
    public void testConvert() {
        assertThat(deepLinkProductConverter.convert(WEB_LINK), is(LinkDTO.of("ty://?Page=Product&ContentId=1925865&MerchantId=105064", LinkType.DEEP_LINK)));
    }
}