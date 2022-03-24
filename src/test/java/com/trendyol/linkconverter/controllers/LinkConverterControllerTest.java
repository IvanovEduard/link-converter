package com.trendyol.linkconverter.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendyol.linkconverter.dto.DeeplinkToConvertDTO;
import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.dto.WebLinkToConvertDTO;
import com.trendyol.linkconverter.services.LinkConverterProcessManager;
import com.trendyol.linkconverter.types.ErrorType;
import com.trendyol.linkconverter.types.LinkType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.trendyol.linkconverter.services.validation.ValidationErrorMessage.ERROR_INVALID_LINK;
import static com.trendyol.linkconverter.services.validation.ValidationErrorMessage.ERROR_LINK_SHOULD_BE_PRESENT;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LinkConverterController.class)
class LinkConverterControllerTest {
    public final static String WEB_TO_DEEP_URL = "/api/linkConvert/webToDeeplink";
    public final static String DEEP_TO_WEB_URL = "/api/linkConvert/deeplinkToWeb";
    public final static String WEB_LINK_TEST = "https://www.trendyol.com/casio/saat-p-1925865?boutiqueId=439892&merchantId=105064";
    public final static String DEEP_LINK_TEST = "ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064";

    @MockBean
    private LinkConverterProcessManager linkConverterProcessManager;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void convertToDeeplink() throws Exception {
        WebLinkToConvertDTO requestBody = new WebLinkToConvertDTO();
        requestBody.setLink(WEB_LINK_TEST);
        LinkDTO expectedResponse = LinkDTO.of(DEEP_LINK_TEST, LinkType.DEEP_LINK);

        when(linkConverterProcessManager.startLinkConvertProcesses(LinkDTO.of(WEB_LINK_TEST, LinkType.WEB_LINK)))
                .thenReturn(LinkDTO.of(DEEP_LINK_TEST, LinkType.DEEP_LINK));

        mockMvc.perform(post(WEB_TO_DEEP_URL)
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link", is(expectedResponse.getLink())))
                .andExpect(jsonPath("$.linkType", is(expectedResponse.getLinkType().name())));
    }

    @Test
    void convertToWeblink() throws Exception {
        DeeplinkToConvertDTO requestBody = new DeeplinkToConvertDTO();
        requestBody.setLink(DEEP_LINK_TEST);
        LinkDTO expectedResponse = LinkDTO.of(WEB_LINK_TEST, LinkType.WEB_LINK);

        when(linkConverterProcessManager.startLinkConvertProcesses(LinkDTO.of(DEEP_LINK_TEST, LinkType.DEEP_LINK)))
                .thenReturn(LinkDTO.of(WEB_LINK_TEST, LinkType.WEB_LINK));

        mockMvc.perform(post(DEEP_TO_WEB_URL)
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link", is(expectedResponse.getLink())))
                .andExpect(jsonPath("$.linkType", is(expectedResponse.getLinkType().name())));
    }

    @Test
    void testCaseWhenWebLinkIsInvalid() throws Exception {
        WebLinkToConvertDTO requestBody = new WebLinkToConvertDTO();
        requestBody.setLink("https://www.trend.com");

        mockMvc.perform(post(WEB_TO_DEEP_URL)
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("$[0].errorMessage", is(ERROR_INVALID_LINK)))
                .andExpect(jsonPath("$[0].errorType", is(ErrorType.VALIDATION_ERROR.name())));
    }

    @Test
    void testCaseWhenDeepLinkIsInvalid() throws Exception {
        DeeplinkToConvertDTO requestBody = new DeeplinkToConvertDTO();
        requestBody.setLink("t://Page=1");

        mockMvc.perform(post(DEEP_TO_WEB_URL)
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("$[0].errorMessage", is(ERROR_INVALID_LINK)))
                .andExpect(jsonPath("$[0].errorType", is(ErrorType.VALIDATION_ERROR.name())));
    }

    @Test
    void testCaseWhenWebLinkIsEmpty() throws Exception {
        WebLinkToConvertDTO requestBody = new WebLinkToConvertDTO();
        requestBody.setLink("");

        mockMvc.perform(post(WEB_TO_DEEP_URL)
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("$[0].errorMessage", is(ERROR_LINK_SHOULD_BE_PRESENT)))
                .andExpect(jsonPath("$[0].errorType", is(ErrorType.VALIDATION_ERROR.name())));
    }

    @Test
    void testCaseWhenDeepLinkIsEmpty() throws Exception {
        DeeplinkToConvertDTO requestBody = new DeeplinkToConvertDTO();
        requestBody.setLink("");

        mockMvc.perform(post(DEEP_TO_WEB_URL)
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("$[0].errorMessage", is(ERROR_LINK_SHOULD_BE_PRESENT)))
                .andExpect(jsonPath("$[0].errorType", is(ErrorType.VALIDATION_ERROR.name())));
    }
}