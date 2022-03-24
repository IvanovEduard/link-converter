package com.trendyol.linkconverter.db.service;

import com.trendyol.linkconverter.dto.LinkDTO;

import java.util.Optional;

/**
 * Interface for access manage DB data.
 */
public interface LinksStorageService {
    void saveResultOfConverting(LinkDTO originalLinkDTO, LinkDTO convertedLinkDTO);

    Optional<LinkDTO> findResultOfConvertingByHashOfOriginalLink(LinkDTO linkDTO);
}
