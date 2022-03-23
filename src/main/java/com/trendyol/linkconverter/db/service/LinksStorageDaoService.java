package com.trendyol.linkconverter.db.service;

import com.trendyol.linkconverter.dto.LinkDTO;

import java.util.Optional;

public interface LinksStorageDaoService {
    void saveResultOfConverting(LinkDTO originalLinkDTO, LinkDTO convertedLinkDTO);

    Optional<LinkDTO> findResultOfConvertingByHashOfOriginalLink(LinkDTO linkDTO);
}
