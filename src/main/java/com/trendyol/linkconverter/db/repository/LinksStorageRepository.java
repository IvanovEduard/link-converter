package com.trendyol.linkconverter.db.repository;

import com.trendyol.linkconverter.db.entity.LinksStorage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for entity {@link LinksStorage}.
 */
public interface LinksStorageRepository extends JpaRepository<LinksStorage, Long> {
    LinksStorage findTopByLinkHash(String linkHash);

    LinksStorage findTopByRelatedLinkId(long relatedLinkId);
}