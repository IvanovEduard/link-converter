package com.trendyol.linkconverter.db.service.impl;

import com.trendyol.linkconverter.db.entity.LinksStorage;
import com.trendyol.linkconverter.db.repository.LinksStorageRepository;
import com.trendyol.linkconverter.db.service.LinksStorageService;
import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.services.cache.CacheService;
import com.trendyol.linkconverter.services.utils.HashGenerator;
import com.trendyol.linkconverter.types.LinkType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

import static java.util.Objects.nonNull;

/**
 * Interface implementation for access manage DB data.
 */
@Repository
@RequiredArgsConstructor
public class LinksStorageServiceImpl implements LinksStorageService, CacheService<LinkDTO, LinkDTO> {
    private final LinksStorageRepository linksStorageRepository;
    private final HashGenerator hashGenerator;

    /**
     * Method for save the input link and the result of converting.
     * Here the three main steps:
     * <ul>
     * <li>generate the hash for link
     * <li>save original data
     * <li>save the result of converting with ID of original data
     * </ul>
     *
     * @param originalLinkDTO  - original data for converting
     * @param convertedLinkDTO - converting result
     */
    @Override
    @Transactional
    public void saveResultOfConverting(final LinkDTO originalLinkDTO, final LinkDTO convertedLinkDTO) {
        convertToEntityWithHashEnrich(originalLinkDTO).ifPresent(originalLinkStorage ->
                {
                    LinksStorage savedOriginalLink = linksStorageRepository.save(originalLinkStorage);
                    convertToEntityWithHashEnrich(convertedLinkDTO, savedOriginalLink.getId())
                            .ifPresent(linksStorageRepository::save);
                }
        );
    }

    /**
     * In this method we try to find the existing converted result in DB
     * by <b>hash</b> ({@link HashGenerator#generate(String link)}) with using original link.
     * Then if we found the entity by <b>hash</b> we know that this is link is already handled,
     * and we can get the existing result by two ways:
     * <ul>
     *     <li>if fetched entity includes the <b>relatedLinkId</b> we try find entity by <b>id</b> where <b>id</b>
     *     should be equal to <b>relatedLinkId</b> of entity which fetched by <b>hash</b></li>
     *     <li>if fetched entity not includes the <b>relatedLinkId</b> we try find entity by <b>relatedLinkId</b> where <b>relatedLinkId</b>
     *           should be equal to <b>id</b> of entity which fetched by <b>hash</b></li>
     * <ul/>
     *
     * @param linkDTO consist the link for converting, and the type of link {@link LinkType}
     * @return existing result of converting from DB
     */
    @Override
    public Optional<LinkDTO> findResultOfConvertingByHashOfOriginalLink(final LinkDTO linkDTO) {
        return hashGenerator.generate(linkDTO.getLink())
                .map(linksStorageRepository::findTopByLinkHash)
                .flatMap(linksStorage -> {
                    if (nonNull(linksStorage.getRelatedLinkId())) {
                        return linksStorageRepository.findById(linksStorage.getRelatedLinkId());
                    }
                    return Optional.ofNullable(linksStorageRepository.findTopByRelatedLinkId(linksStorage.getId()));
                })
                .map(linksStorage -> LinkDTO.of(linksStorage.getLink(), linksStorage.getLinkType()));
    }

    private Optional<LinksStorage> convertToEntityWithHashEnrich(final LinkDTO linkDTO) {
        return convertToEntityWithHashEnrich(linkDTO, null);
    }

    /**
     * This method used for prepare the converted link result for saving to DB
     *
     * @param linkDTO   consist the converted link, and the type of link {@link LinkType}
     * @param relatedId the id of original entity (for which performed the converting) which saved to DB
     * @return enriched entity for saving
     */
    private Optional<LinksStorage> convertToEntityWithHashEnrich(final LinkDTO linkDTO, final Long relatedId) {
        return hashGenerator.generate(linkDTO.getLink()).map(hash -> {
            LinksStorage linksStorage = new LinksStorage();
            linksStorage.setLink(linkDTO.getLink());
            linksStorage.setLinkHash(hash);
            linksStorage.setLinkType(linkDTO.getLinkType());
            linksStorage.setRelatedLinkId(relatedId);
            return linksStorage;
        });
    }

    @Override
    public LinkDTO getData(LinkDTO key) {
        return findResultOfConvertingByHashOfOriginalLink(key).orElse(null);
    }
}
