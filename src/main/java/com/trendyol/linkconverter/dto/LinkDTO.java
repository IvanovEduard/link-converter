package com.trendyol.linkconverter.dto;

import com.trendyol.linkconverter.types.LinkType;
import lombok.Value;

/**
 * Data transfer object which used in the whole converting process flow.
 */
@Value(staticConstructor = "of")
public class LinkDTO {
    String link;
    LinkType linkType;
}
