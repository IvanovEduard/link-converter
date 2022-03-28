package com.trendyol.linkconverter.services;

import com.trendyol.linkconverter.db.service.LinksStorageService;
import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.services.cache.LoadingCacheStore;
import com.trendyol.linkconverter.services.executor.LinkConvertExecutor;
import com.trendyol.linkconverter.services.executor.ProductLinkConvertExecutor;
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
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LinkConverterProcessManagerTest {
    @InjectMocks
    private LinkConverterProcessManager linkConverterProcessManager;
    @Mock
    private LinksStorageService linksStorageService;
    @Mock
    private List<LinkConvertExecutor> linkConvertExecutors;
    @Mock
    private ProductLinkConvertExecutor productLinkConvertRouter;
    @Mock
    private LoadingCacheStore<LinkDTO, LinkDTO> linksStorageCache;

    private final static String WEB_LINK_TEST = "https://www.trendyol.com/casio/saat-p-1925865?boutiqueId=439892&merchantId=105064";
    private final static String DEEP_LINK_TEST = "ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064";


    @Before
    public void setUp() {
        ReflectionTestUtils.setField(linkConverterProcessManager, "linkConvertExecutorMapper", Map.of(PageType.PRODUCT, productLinkConvertRouter));
    }

    @Test
    public void testFullProcess() throws ExecutionException {
        LinkDTO linkDTO = LinkDTO.of(WEB_LINK_TEST, LinkType.WEB_LINK);
        LinkDTO convertedLinkDTO = LinkDTO.of(DEEP_LINK_TEST, LinkType.DEEP_LINK);
        when(productLinkConvertRouter.convert(linkDTO)).thenReturn(convertedLinkDTO);

        LinkDTO result = linkConverterProcessManager.startLinkConvertProcesses(LinkDTO.of(WEB_LINK_TEST, LinkType.WEB_LINK));

        verify(linksStorageService).saveResultOfConverting(linkDTO, convertedLinkDTO);
        assertThat(result, is(convertedLinkDTO));
    }

    @Test
    public void testWhenLinkExistInDB() throws ExecutionException {
        LinkDTO linkDTO = LinkDTO.of(WEB_LINK_TEST, LinkType.WEB_LINK);
        LinkDTO convertedLinkDTO = LinkDTO.of(DEEP_LINK_TEST, LinkType.DEEP_LINK);
        when(linksStorageCache.get(linkDTO)).thenReturn(convertedLinkDTO);

        LinkDTO result = linkConverterProcessManager.startLinkConvertProcesses(LinkDTO.of(WEB_LINK_TEST, LinkType.WEB_LINK));

        verify(linksStorageService, never()).saveResultOfConverting(linkDTO, convertedLinkDTO);
        verify(productLinkConvertRouter, never()).convert(linkDTO);
        assertThat(result, is(convertedLinkDTO));
    }
}