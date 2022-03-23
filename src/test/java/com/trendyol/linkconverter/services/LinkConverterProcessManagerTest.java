package com.trendyol.linkconverter.services;

import com.trendyol.linkconverter.db.service.LinksStorageDaoService;
import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.services.router.LinkConvertExecutor;
import com.trendyol.linkconverter.services.router.ProductLinkConvertExecutor;
import com.trendyol.linkconverter.types.LinkType;
import com.trendyol.linkconverter.types.PageType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LinkConverterProcessManagerTest {
    @InjectMocks
    private LinkConverterProcessManager linkConverterProcessManager;
    @Mock
    private LinksStorageDaoService linksStorageDaoService;
    @Mock
    private List<LinkConvertExecutor> linkConvertExecutors;
    @Mock
    private ProductLinkConvertExecutor productLinkConvertRouter;

    private final static String WEB_LINK_TEST = "https://www.trendyol.com/casio/saat-p-1925865?boutiqueId=439892&merchantId=105064";
    private final static String WEB_LINK_TEST2 = "https://www.trendyol.com/casio/saat-p-1925865?boutiqueId=439892&merchantId=1050642";
    private final static String WEB_LINK_TEST3 = "https://www.trendyol.com/casio/saat-p-1925865?boutiqueId=439892&merchantId=1050643";
    private final static String DEEP_LINK_TEST = "ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064";
    private final static String DEEP_LINK_TEST2 = "ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=1050642";
    private final static String DEEP_LINK_TEST3 = "ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=1050643";


    @Before
    public void setUp() {
        ReflectionTestUtils.setField(linkConverterProcessManager, "linkConvertRoutersMapper", Map.of(PageType.PRODUCT, productLinkConvertRouter));
    }

    @Test
    public void testFullProcess() throws ExecutionException {
        LinkDTO linkDTO = LinkDTO.of(WEB_LINK_TEST, LinkType.WEB_URL);
        LinkDTO convertedLinkDTO = LinkDTO.of(DEEP_LINK_TEST, LinkType.DEEP_LINK);
        when(productLinkConvertRouter.convert(linkDTO)).thenReturn(convertedLinkDTO);
        when(linksStorageDaoService.findResultOfConvertingByHashOfOriginalLink(linkDTO)).thenReturn(Optional.empty());

        LinkDTO result = linkConverterProcessManager.startLinkConvertProcesses(LinkDTO.of(WEB_LINK_TEST, LinkType.WEB_URL));

        verify(linksStorageDaoService).saveResultOfConverting(linkDTO, convertedLinkDTO);
        assertThat(result, is(convertedLinkDTO));
    }

    @Test
    public void testWhenLinkExistInDB() throws ExecutionException {
        LinkDTO linkDTO = LinkDTO.of(WEB_LINK_TEST, LinkType.WEB_URL);
        LinkDTO convertedLinkDTO = LinkDTO.of(DEEP_LINK_TEST, LinkType.DEEP_LINK);
        when(linksStorageDaoService.findResultOfConvertingByHashOfOriginalLink(linkDTO)).thenReturn(Optional.of(convertedLinkDTO));

        LinkDTO result = linkConverterProcessManager.startLinkConvertProcesses(LinkDTO.of(WEB_LINK_TEST, LinkType.WEB_URL));

        verify(linksStorageDaoService, never()).saveResultOfConverting(linkDTO, convertedLinkDTO);
        verify(productLinkConvertRouter, never()).convert(linkDTO);
        assertThat(result, is(convertedLinkDTO));
    }

    @Test
    public void testCacheUsing() throws ExecutionException {
        LinkDTO linkDTO2 = LinkDTO.of(WEB_LINK_TEST2, LinkType.WEB_URL);
        LinkDTO linkDTO3 = LinkDTO.of(WEB_LINK_TEST3, LinkType.WEB_URL);
        LinkDTO convertedLinkDTO2 = LinkDTO.of(DEEP_LINK_TEST2, LinkType.DEEP_LINK);
        LinkDTO convertedLinkDTO3 = LinkDTO.of(DEEP_LINK_TEST3, LinkType.DEEP_LINK);

        when(productLinkConvertRouter.convert(linkDTO2)).thenReturn(convertedLinkDTO2);
        when(productLinkConvertRouter.convert(linkDTO3)).thenReturn(convertedLinkDTO3);

        when(linksStorageDaoService.findResultOfConvertingByHashOfOriginalLink(linkDTO2)).thenReturn(Optional.empty()).thenReturn(Optional.of(convertedLinkDTO2));
        when(linksStorageDaoService.findResultOfConvertingByHashOfOriginalLink(linkDTO3)).thenReturn(Optional.empty()).thenReturn(Optional.of(convertedLinkDTO3));

        LinkDTO result2 = linkConverterProcessManager.startLinkConvertProcesses(linkDTO2);
        linkConverterProcessManager.startLinkConvertProcesses(linkDTO3);
        linkConverterProcessManager.startLinkConvertProcesses(linkDTO3);
        LinkDTO result3 = linkConverterProcessManager.startLinkConvertProcesses(linkDTO3);

        verify(linksStorageDaoService).saveResultOfConverting(linkDTO2, convertedLinkDTO2);
        verify(linksStorageDaoService).saveResultOfConverting(linkDTO3, convertedLinkDTO3); // remember that by default times for verify() method is eq 1, here we check that saveResultOfConverting called 1 time for linkDTO3
        verify(productLinkConvertRouter).convert(linkDTO2);
        verify(productLinkConvertRouter).convert(linkDTO3);
        assertThat(result2, is(convertedLinkDTO2));
        assertThat(result3, is(convertedLinkDTO3));
    }
}