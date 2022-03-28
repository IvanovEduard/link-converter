package com.trendyol.linkconverter.services;

import com.trendyol.linkconverter.db.service.LinksStorageService;
import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.services.cache.LoadingCacheStore;
import com.trendyol.linkconverter.services.executor.LinkConvertExecutor;
import com.trendyol.linkconverter.services.utils.PageTypeDetector;
import com.trendyol.linkconverter.types.LinkType;
import com.trendyol.linkconverter.types.PageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;


/**
 * Managing the process of link converting.
 */
@Slf4j
@Component
public class LinkConverterProcessManager {
    private final LinksStorageService linksStorageService;
    private final LoadingCacheStore<LinkDTO, LinkDTO> linksStorageCache;
    private final Map<PageType, LinkConvertExecutor> linkConvertExecutorMapper;

    public LinkConverterProcessManager(List<LinkConvertExecutor> linkConvertExecutors,
                                       LinksStorageService linksStorageService,
                                       LoadingCacheStore<LinkDTO, LinkDTO> linksStorageCache) {
        this.linkConvertExecutorMapper = linkConvertExecutors.stream().collect(Collectors.toMap(LinkConvertExecutor::getPageType, Function.identity()));
        this.linksStorageService = linksStorageService;
        this.linksStorageCache = linksStorageCache;
    }

    /**
     * In this method checked or the link already processed, and if true the existing result will be returned.
     * Otherwise, the convert process will be start and the out result will be saved to DB.
     *
     * @param linkDTO consist the link for converting, and the type linkType {@link LinkType}
     * @return converted link with appropriate linkType
     */
    public LinkDTO startLinkConvertProcesses(final LinkDTO linkDTO) throws ExecutionException {
        LinkDTO convertedSavedResult = linksStorageCache.get(linkDTO);

        if (nonNull(convertedSavedResult)) {
            log.info("The link {} is already processed", linkDTO.getLink());
            return convertedSavedResult;
        }
        log.info("Start convert link - {} type - {}", linkDTO.getLink(), linkDTO.getLinkType());
        LinkDTO convertedLinkDTO = linkConvertExecutorMapper.get(PageTypeDetector.detectPageType(linkDTO.getLink())).convert(linkDTO);
        linksStorageService.saveResultOfConverting(linkDTO, convertedLinkDTO);
        log.info("Convert process finished successfully - {} type - {}", linkDTO.getLink(), linkDTO.getLinkType());
        return convertedLinkDTO;
    }
}
