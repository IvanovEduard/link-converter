package com.trendyol.linkconverter.services.executor;

import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.services.LinkConverterProcessManager;
import com.trendyol.linkconverter.services.converter.BaseLinkConverter;
import com.trendyol.linkconverter.types.LinkType;
import com.trendyol.linkconverter.types.PageType;

/**
 * Routing the converting process
 */
public interface LinkConvertExecutor {

    /**
     * This default method which define the type of converting according
     * to <b>linkType</b> of input data.
     *
     * @param linkDTO consist the link for converting, and the type of link {@link LinkType}
     * @return {@link LinkDTO} that consist the result of converting, and the type of link {@link LinkType}
     */
    default LinkDTO convert(final LinkDTO linkDTO) {
        String link = linkDTO.getLink();
        if (LinkType.WEB_URL.equals(linkDTO.getLinkType())) {
            return toDeepLinkConverter().convert(link);
        }
        return toWebLinkConverter().convert(link);
    }

    /**
     * Define converter component for deeplink
     *
     * @return component for converting to deeplink
     */
    BaseLinkConverter toDeepLinkConverter();

    /**
     * Define converter component for web link
     *
     * @return component for converting to web link
     */
    BaseLinkConverter toWebLinkConverter();

    /**
     * This method need for detect the required converter for <b>link</b> in {@link LinkConverterProcessManager}
     *
     * @return supported <b>pageType</b>
     */
    PageType getPageType();
}
