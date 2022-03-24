package com.trendyol.linkconverter.services.converter;

import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.types.LinkType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Base abstract converter which include the main rules for build the converted link
 */
public abstract class BaseLinkConverter {
    public static final String BASE_DEEPLINK_SCHEME = "ty";
    public static final String BASE_WEB_SCHEME = "https";
    public static final String BASE_WEB_HOST = "www.trendyol.com";
    public static final String APP_LINK_PARAMETER_PAGE = "Page";
    public static final String APP_LINK_PARAMETER_CAMPAIGN_ID = "CampaignId";
    public static final String APP_LINK_PARAMETER_CONTENT_ID = "ContentId";
    public static final String APP_LINK_PARAMETER_MERCHANT_ID = "MerchantId";
    public static final String WEB_LINK_PATH_BRAND = "brand";
    public static final String WEB_LINK_PATH_SEARCH = "sr";
    public static final String WEB_LINK_PARAMETER_CAMPAIGN_ID = "boutiqueId";
    public static final String WEB_LINK_PATH_CONTENT_ID = "name-p-";
    public static final String WEB_LINK_PARAMETER_MERCHANT_ID = "merchantId";

    public static final String WEB_LINK_PARAMETER_QUERY = "q";
    public static final String APP_LINK_PARAMETER_QUERY = "Query";

    public static final String SYMBOL_SLASH = "/";
    public static final String EMPTY_STR = "";

    /**
     * Building the link parameters
     *
     * @param link original link which will be converted
     * @return parameters of converted link
     */
    protected abstract MultiValueMap<String, String> queryParameters(String link);

    /**
     * Method for determine the protocol like http, https etc
     *
     * @return determined <b>scheme</b>
     */
    protected abstract String scheme();

    /**
     * Method for determine <b>host</b>
     *
     * @return determined <b>host</b>
     */
    protected abstract String host();

    /**
     * Method for determine which <b>path</b> should be in the link
     *
     * @return determined <b>path</b>
     */
    protected abstract String path(String link);

    /**
     * Base method for building the converted link
     *
     * @param link original link which will be converted
     * @return {@link LinkDTO} which consist the converted link, and the type of link {@link LinkType}
     */
    public LinkDTO convert(final String link) {
        UriComponentsBuilder https = UriComponentsBuilder.newInstance().scheme(scheme()).host(host()).path(path(link)).queryParams(queryParameters(link));
        return LinkDTO.of(URLDecoder.decode(https.toUriString(), StandardCharsets.UTF_8), outputLinkType());
    }

    /**
     * This method required for define the type of converted Link
     *
     * @return {@link LinkType}
     */
    protected abstract LinkType outputLinkType();

    /**
     * This method perform the fetching the parameters from link
     *
     * @param link the url string
     * @return map of parameters from the url string
     */
    protected Map<String, String> getQueryParams(final String link) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(link).build();
        return uriComponents.getQueryParams().toSingleValueMap();
    }
}
