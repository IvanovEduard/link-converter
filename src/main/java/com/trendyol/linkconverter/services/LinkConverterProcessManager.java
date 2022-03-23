package com.trendyol.linkconverter.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.trendyol.linkconverter.controllers.ErrorInterceptorController;
import com.trendyol.linkconverter.db.service.LinksStorageDaoService;
import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.services.router.LinkConvertExecutor;
import com.trendyol.linkconverter.services.utils.PageTypeDetector;
import com.trendyol.linkconverter.services.validation.EntityIsMissingInDBException;
import com.trendyol.linkconverter.types.LinkType;
import com.trendyol.linkconverter.types.PageType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Managing the process of link converting.
 */
@Slf4j
@Component
public class LinkConverterProcessManager {
    private final LinksStorageDaoService linksStorageDaoService;
    private final Map<PageType, LinkConvertExecutor> linkConvertExecutorMapper;

    public LinkConverterProcessManager(List<LinkConvertExecutor> linkConvertExecutors, LinksStorageDaoService linksStorageDaoService) {
        this.linkConvertExecutorMapper = linkConvertExecutors.stream().collect(Collectors.toMap(LinkConvertExecutor::getPageType, Function.identity()));
        this.linksStorageDaoService = linksStorageDaoService;
    }

    private final LoadingCache<LinkDTO, Optional<LinkDTO>> loadingCache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .build(new CacheLoader<>() {
                @Override
                public Optional<LinkDTO> load(final @NonNull LinkDTO linkDTO) throws EntityIsMissingInDBException {
                    Optional<LinkDTO> linkDTOOptional = linksStorageDaoService.findResultOfConvertingByHashOfOriginalLink(linkDTO);
                    if (linkDTOOptional.isEmpty()) {
                        throw new EntityIsMissingInDBException();
                    }
                    return linkDTOOptional;
                }
            });

    /**
     * In this method checked or the link already processed, and if true the existing result will be returned.
     * Otherwise, the convert process will be start and the out result will be saved to DB.
     *
     * @param linkDTO consist the link for converting, and the type linkType {@link LinkType}
     * @return converted link with appropriate linkType
     */
    public LinkDTO startLinkConvertProcesses(final LinkDTO linkDTO) throws ExecutionException {
        Optional<LinkDTO> byHashAndRelatedId = getLinkDTOWithUsingCache(linkDTO);

        if (byHashAndRelatedId.isPresent()) {
            log.info("The link {} is already processed", linkDTO.getLink());
            return byHashAndRelatedId.get();
        }
        log.info("Start convert link - {} type - {}", linkDTO.getLink(), linkDTO.getLinkType());
        LinkDTO convertedLinkDTO = linkConvertExecutorMapper.get(PageTypeDetector.detectPageType(linkDTO.getLink())).convert(linkDTO);
        linksStorageDaoService.saveResultOfConverting(linkDTO, convertedLinkDTO);
        log.info("Convert process finished successfully - {} type - {}", linkDTO.getLink(), linkDTO.getLinkType());
        return convertedLinkDTO;
    }

    /**
     * Fetch entity from DB with using cache.
     * In case when the result is empty we throw the custom EntityIsMissingInDBException exception
     * for avoiding hold in cache the empty <b>Optional</b> element.
     * The exception throwing is located in initializing cache {@link LinkConverterProcessManager#loadingCache}
     * In case some unexpected error during data fetching we throw the exception from this method
     * which should be handled in {@link ErrorInterceptorController#handleApplicationException(Exception, HandlerMethod)}
     *
     * @param linkDTO consist the link for converting, and the type linkType {@link LinkType}
     * @return fetched entity from DB
     */
    private Optional<LinkDTO> getLinkDTOWithUsingCache(final LinkDTO linkDTO) throws ExecutionException {
        try {
            return loadingCache.get(linkDTO);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof EntityIsMissingInDBException) {
                return Optional.empty();
            }
            log.error("Error occurs during load data by cache", e);
            throw e;
        }
    }
}
