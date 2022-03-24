package com.trendyol.linkconverter.db.service.impl;

import com.trendyol.linkconverter.db.entity.LinksStorage;
import com.trendyol.linkconverter.db.repository.LinksStorageRepository;
import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.services.utils.HashGenerator;
import com.trendyol.linkconverter.types.LinkType;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({
        LinksStorageServiceImpl.class,
        com.trendyol.linkconverter.services.utils.HashGenerator.class
})
@TestPropertySource("classpath:source.properties")
public class LinksStorageServiceImplIntegrationTest {
    @Autowired
    private LinksStorageRepository linksStorageRepository;

    @Autowired
    private LinksStorageServiceImpl linksStorageDaoService;
    @Autowired
    private HashGenerator hashGenerator;

    @ClassRule
    public static GenericContainer simpleWebServer = new GenericContainer("mysql")
            .withExposedPorts(33060)
            .withEnv(Map.of(
                    "MYSQL_ALLOW_EMPTY_PASSWORD", "true",
                    "MYSQL_DATABASE", "trendyol_db"
            ));

    private static final String WEB_LINK = "https://www.trendyol.com/sr?q=çocuk%20yemeği";
    private static final String DEEP_LINK = "ty://?Page=Search&Query=çocuk%20yemeği";

    @Test
    public void testSaveResultOfConverting() {
        linksStorageDaoService.saveResultOfConverting(LinkDTO.of(DEEP_LINK, LinkType.DEEP_LINK), LinkDTO.of(WEB_LINK, LinkType.WEB_LINK));
        Optional<String> webLinkHash = hashGenerator.generate(WEB_LINK);
        Optional<String> deepLinkHash = hashGenerator.generate(DEEP_LINK);
        LinksStorage webLinksStorage = linksStorageRepository.findTopByLinkHash(webLinkHash.get());
        LinksStorage deepLinksStorage = linksStorageRepository.findTopByLinkHash(deepLinkHash.get());
        assertThat(webLinksStorage.getLink(), is(WEB_LINK));
        assertThat(deepLinksStorage.getLink(), is(DEEP_LINK));
        assertThat(webLinksStorage.getLinkType(), is(LinkType.WEB_LINK));
        assertThat(deepLinksStorage.getLinkType(), is(LinkType.DEEP_LINK));
        assertThat(webLinksStorage.getRelatedLinkId(), is(deepLinksStorage.getId()));
        assertThat(deepLinksStorage.getRelatedLinkId(), is(nullValue()));
    }

    @Test
    public void testFindResultOfConvertingByHashOfOriginalLinkWhenDeepIsOriginal() {
        LinkDTO webLinkDTO = LinkDTO.of(WEB_LINK, LinkType.WEB_LINK);
        LinkDTO deepLinkDTO = LinkDTO.of(DEEP_LINK, LinkType.DEEP_LINK);
        linksStorageDaoService.saveResultOfConverting(deepLinkDTO, webLinkDTO);

        Optional<LinkDTO> resultFindByDeeplink = linksStorageDaoService.findResultOfConvertingByHashOfOriginalLink(deepLinkDTO);
        Optional<LinkDTO> resultFindByWeblink = linksStorageDaoService.findResultOfConvertingByHashOfOriginalLink(webLinkDTO);

        assertThat(resultFindByDeeplink, is(Optional.of(webLinkDTO)));
        assertThat(resultFindByWeblink, is(Optional.of(deepLinkDTO)));
    }

    @Test
    public void testFindResultOfConvertingByHashOfOriginalLinkWhenWebIsOriginal() {
        LinkDTO webLinkDTO = LinkDTO.of(WEB_LINK, LinkType.WEB_LINK);
        LinkDTO deepLinkDTO = LinkDTO.of(DEEP_LINK, LinkType.DEEP_LINK);
        linksStorageDaoService.saveResultOfConverting(webLinkDTO, deepLinkDTO);

        Optional<LinkDTO> resultFindByDeeplink = linksStorageDaoService.findResultOfConvertingByHashOfOriginalLink(deepLinkDTO);
        Optional<LinkDTO> resultFindByWeblink = linksStorageDaoService.findResultOfConvertingByHashOfOriginalLink(webLinkDTO);

        assertThat(resultFindByDeeplink, is(Optional.of(webLinkDTO)));
        assertThat(resultFindByWeblink, is(Optional.of(deepLinkDTO)));
    }
}