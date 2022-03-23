package com.trendyol.linkconverter.controllers;

import com.trendyol.linkconverter.dto.DeeplinkToConvertDTO;
import com.trendyol.linkconverter.dto.LinkDTO;
import com.trendyol.linkconverter.dto.WebLinkToConvertDTO;
import com.trendyol.linkconverter.services.LinkConverterProcessManager;
import com.trendyol.linkconverter.types.LinkType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;


/**
 * The link converting Controller.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/linkConvert")
public class LinkConverterController {
    private final LinkConverterProcessManager linkConverterProcessManager;


    /**
     * Convert Web Link to Deeplink.
     *
     * @param webLinkToConvertDTO the InputLinkDataDTO consist the link for converting
     * @return it is converted link with appropriate link type {@link LinkType}
     */
    @PostMapping("/webToDeeplink")
    @ResponseStatus(HttpStatus.OK)
    public LinkDTO convertToDeeplink(@NonNull @Valid @RequestBody WebLinkToConvertDTO webLinkToConvertDTO) throws ExecutionException {
        String link = webLinkToConvertDTO.getLink();
        log.info("Start converting web link - {} to deeplink", link);
        return linkConverterProcessManager.startLinkConvertProcesses(LinkDTO.of(link, LinkType.WEB_URL));
    }

    /**
     * Convert Deeplink to Web link.
     *
     * @param deeplinkToConvertDTO the InputLinkDataDTO consist the link for converting
     * @return it is converted link with appropriate link type {@link LinkType}
     */
    @PostMapping("/deeplinkToWeb")
    @ResponseStatus(HttpStatus.OK)
    public LinkDTO convertToWeb(@NonNull @Valid @RequestBody DeeplinkToConvertDTO deeplinkToConvertDTO) throws ExecutionException {
        String link = deeplinkToConvertDTO.getLink();
        log.info("Start converting deeplink - {} to web link", link);
        return linkConverterProcessManager.startLinkConvertProcesses(LinkDTO.of(link, LinkType.DEEP_LINK));
    }

}
