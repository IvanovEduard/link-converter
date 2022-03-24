package com.trendyol.linkconverter.services.converter.product;

import com.trendyol.linkconverter.InjectPropertiesTest;
import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.types.LinkType;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Map;

import static com.trendyol.linkconverter.services.utils.QueryConstants.WEB_LINK_PARAMETER_MERCHANT_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class WebLinkProductConverterTest extends InjectPropertiesTest {
    @SpyBean
    private WebLinkProductConverter webLinkProductConverter;

    private static final String DEEP_LINK = "ty://?Page=Product&ContentId=1925865&MerchantId=105064";
    private static final String DEEP_LINK_WITH_CAMPAIGN = "ty://?Page=Product&ContentId=1925865&CampaignId=439892";

    @Test
    public void testOutputLinkType() {
        assertThat(webLinkProductConverter.outputLinkType(), is(LinkType.WEB_LINK));
    }

    @Test
    public void testBuildPath() {
        assertThat(webLinkProductConverter.path(DEEP_LINK), is("brand/name-p-1925865"));
    }

    @Test
    public void testBuildLinkParameters() {
        assertThat(webLinkProductConverter.queryParameters(DEEP_LINK).toSingleValueMap(), is(Map.of(WEB_LINK_PARAMETER_MERCHANT_ID, "105064")));
    }

    @Test
    public void testConvert() {
        assertThat(webLinkProductConverter.convert(DEEP_LINK), is(LinkDTO.of("https://www.trendyol.com/brand/name-p-1925865?merchantId=105064", LinkType.WEB_LINK)));
    }

    @Test
    public void testConvertWhenInDeeplinkCampaignIdPresent() {
        assertThat(webLinkProductConverter.convert(DEEP_LINK_WITH_CAMPAIGN), is(LinkDTO.of("https://www.trendyol.com/brand/name-p-1925865?boutiqueId=439892", LinkType.WEB_LINK)));
    }
}