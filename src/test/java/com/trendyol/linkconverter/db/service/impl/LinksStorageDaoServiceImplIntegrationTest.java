package com.trendyol.linkconverter.db.service.impl;

import com.trendyol.linkconverter.db.entity.LinksStorage;
import com.trendyol.linkconverter.db.repository.LinksStorageRepository;
import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.services.utils.HashGenerator;
import com.trendyol.linkconverter.types.LinkType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({
        com.trendyol.linkconverter.db.service.impl.LinksStorageDaoServiceImpl.class,
        com.trendyol.linkconverter.services.utils.HashGenerator.class
})
public class LinksStorageDaoServiceImplIntegrationTest {
    @Autowired
    private LinksStorageRepository linksStorageRepository;

    @Autowired
    private LinksStorageDaoServiceImpl linksStorageDaoService;
    @Autowired
    private HashGenerator hashGenerator;

    private static final String WEB_LINK = "https://www.trendyol.com/brand/name-p-1925865?boutiqueId=439892&merchantId=1050";
    private static final String DEEP_LINK = "ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064";

    @Test
    public void testSaveResultOfConverting() {
        linksStorageDaoService.saveResultOfConverting(LinkDTO.of(DEEP_LINK, LinkType.DEEP_LINK), LinkDTO.of(WEB_LINK, LinkType.WEB_URL));
        Optional<String> webLinkHash = hashGenerator.generate(WEB_LINK);
        Optional<String> deepLinkHash = hashGenerator.generate(DEEP_LINK);
        LinksStorage webLinksStorage = linksStorageRepository.findTopByLinkHash(webLinkHash.get());
        LinksStorage deepLinksStorage = linksStorageRepository.findTopByLinkHash(deepLinkHash.get());
        assertThat(webLinksStorage.getLink(), is(WEB_LINK));
        assertThat(deepLinksStorage.getLink(), is(DEEP_LINK));
        assertThat(webLinksStorage.getLinkType(), is(LinkType.WEB_URL));
        assertThat(deepLinksStorage.getLinkType(), is(LinkType.DEEP_LINK));
        assertThat(webLinksStorage.getRelatedLinkId(), is(deepLinksStorage.getId()));
        assertThat(deepLinksStorage.getRelatedLinkId(), is(nullValue()));
    }

    @Test
    public void testFindResultOfConvertingByHashOfOriginalLinkWhenDeepIsOriginal() {
        LinkDTO webLinkDTO = LinkDTO.of(WEB_LINK, LinkType.WEB_URL);
        LinkDTO deepLinkDTO = LinkDTO.of(DEEP_LINK, LinkType.DEEP_LINK);
        linksStorageDaoService.saveResultOfConverting(deepLinkDTO, webLinkDTO);

        Optional<LinkDTO> resultFindByDeeplink = linksStorageDaoService.findResultOfConvertingByHashOfOriginalLink(deepLinkDTO);
        Optional<LinkDTO> resultFindByWeblink = linksStorageDaoService.findResultOfConvertingByHashOfOriginalLink(webLinkDTO);

        assertThat(resultFindByDeeplink, is(Optional.of(webLinkDTO)));
        assertThat(resultFindByWeblink, is(Optional.of(deepLinkDTO)));
    }

    @Test
    public void testFindResultOfConvertingByHashOfOriginalLinkWhenWebIsOriginal() {
        LinkDTO webLinkDTO = LinkDTO.of(WEB_LINK, LinkType.WEB_URL);
        LinkDTO deepLinkDTO = LinkDTO.of(DEEP_LINK, LinkType.DEEP_LINK);
        linksStorageDaoService.saveResultOfConverting(webLinkDTO, deepLinkDTO);

        Optional<LinkDTO> resultFindByDeeplink = linksStorageDaoService.findResultOfConvertingByHashOfOriginalLink(deepLinkDTO);
        Optional<LinkDTO> resultFindByWeblink = linksStorageDaoService.findResultOfConvertingByHashOfOriginalLink(webLinkDTO);

        assertThat(resultFindByDeeplink, is(Optional.of(webLinkDTO)));
        assertThat(resultFindByWeblink, is(Optional.of(deepLinkDTO)));
    }
}