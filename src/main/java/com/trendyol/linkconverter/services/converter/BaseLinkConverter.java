package com.trendyol.linkconverter.services.converter;

import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.types.LinkType;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Base abstract converter which include the main rules for build the converted link
 */
public abstract class BaseLinkConverter {
    public static final String BASE_DEEPLINK = "ty://";
    public static final String BASE_WEB_URL = "https://www.trendyol.com";
    public static final String APP_LINK_PARAMETER_PAGE = "Page";
    public static final String APP_LINK_PARAMETER_CAMPAIGN_ID = "CampaignId";
    public static final String APP_LINK_PARAMETER_CONTENT_ID = "ContentId";
    public static final String APP_LINK_PARAMETER_MERCHANT_ID = "MerchantId";
    public static final String WEB_LINK_PATH_BRAND = "brand";
    public static final String WEB_LINK_PARAMETER_CAMPAIGN_ID = "boutiqueId";
    public static final String WEB_LINK_PATH_CONTENT_ID = "name-p-";
    public static final String WEB_LINK_PARAMETER_MERCHANT_ID = "merchantId";

    public static final String WEB_LINK_PARAMETER_QUERY = "q";
    public static final String APP_LINK_PARAMETER_QUERY = "Query";

    public static final String SYMBOL_AMPERSAND = "&";
    public static final String SYMBOL_QUESTION_MARK = "?";
    public static final String SYMBOL_EQUAL = "=";
    public static final String SYMBOL_SLASH = "/";
    public static final String EMPTY_STR = "";

    /**
     * Building the base link with can include the <b>protocol, host</b> and <b>path</b>
     *
     * @param link original link which will be converted
     * @return base part of converted link
     */
    protected abstract String buildBaseLink(String link);

    /**
     * Building the link parameters
     *
     * @param link original link which will be converted
     * @return parameters of converted link
     */
    protected abstract String buildLinkParameters(String link);

    /**
     * Base method for building the converted link
     *
     * @param link original link which will be converted
     * @return {@link LinkDTO} which consist the converted link, and the type of link {@link LinkType}
     */
    public LinkDTO convert(final String link) {
        String convertedLink = buildBaseLink(link) + buildLinkParameters(link);
        return LinkDTO.of(convertedLink, outputLinkType());
    }

    /**
     * This method required for define the type of converted Link
     *
     * @return {@link LinkType}
     */
    protected abstract LinkType outputLinkType();

    /**
     * This method build string parameter by template <b>param=value</b>
     *
     * @param paramName  name of parameter
     * @param paramValue value of parameter
     * @return joined string by template <b>param=value</b>
     */
    protected String buildParamEqual(final String paramName, final String paramValue) {
        return paramName + SYMBOL_EQUAL + paramValue;
    }

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
