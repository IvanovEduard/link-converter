package com.trendyol.linkconverter.services.converter.product;

import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.types.LinkType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WebLinkProductConverterTest {
    private final WebLinkProductConverter webLinkProductConverter = new WebLinkProductConverter();

    private static final String DEEP_LINK = "ty://?Page=Product&ContentId=1925865&MerchantId=105064";
    private static final String DEEP_LINK_WITH_CAMPAIGN = "ty://?Page=Product&ContentId=1925865&CampaignId=439892";

    @Test
    public void testOutputLinkType() {
        assertThat(webLinkProductConverter.outputLinkType(), is(LinkType.WEB_URL));
    }

    @Test
    public void testBuildBaseLink() {
        assertThat(webLinkProductConverter.buildBaseLink(DEEP_LINK), is("https://www.trendyol.com/brand/name-p-1925865"));
    }

    @Test
    public void testBuildLinkParameters() {
        assertThat(webLinkProductConverter.buildLinkParameters(DEEP_LINK), is("?merchantId=105064"));
    }

    @Test
    public void testConvert() {
        assertThat(webLinkProductConverter.convert(DEEP_LINK), is(LinkDTO.of("https://www.trendyol.com/brand/name-p-1925865?merchantId=105064", LinkType.WEB_URL)));
    }

    @Test
    public void testConvertWhenInDeeplinkCampaignIdPresent() {
        assertThat(webLinkProductConverter.convert(DEEP_LINK_WITH_CAMPAIGN), is(LinkDTO.of("https://www.trendyol.com/brand/name-p-1925865?boutiqueId=439892", LinkType.WEB_URL)));
    }
}