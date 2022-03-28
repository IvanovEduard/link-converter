package com.trendyol.linkconverter.services.cache;

import com.trendyol.linkconverter.db.service.impl.LinksStorageServiceImpl;
import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.types.LinkType;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class LoadingCacheStoreTest {
    private final static String WEB_LINK_TEST = "https://www.trendyol.com/casio/saat-p-1925865?boutiqueId=439892&merchantId=105064";
    private final static String DEEP_LINK_TEST = "ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064";


    private LinksStorageServiceImpl cacheService = mock(LinksStorageServiceImpl.class);
    private LoadingCacheStore<LinkDTO, LinkDTO> loadingCacheStore = new LoadingCacheStore<>(3, TimeUnit.SECONDS, 5, cacheService);


    @Test
    public void testCacheUsing() throws ExecutionException {
        LinkDTO linkDTO = LinkDTO.of(WEB_LINK_TEST, LinkType.WEB_LINK);

        LinkDTO convertedLinkDTO = LinkDTO.of(DEEP_LINK_TEST, LinkType.DEEP_LINK);

        when(cacheService.getData(linkDTO)).thenReturn(convertedLinkDTO);

        loadingCacheStore.get(linkDTO);
        loadingCacheStore.get(linkDTO);
        LinkDTO actualLinkDTO = loadingCacheStore.get(linkDTO);

        assertEquals(actualLinkDTO, convertedLinkDTO);
        verify(cacheService).getData(linkDTO); // by default times for verify() method is eq 1, here we check that cacheService called 1 time instead 3
    }

}