package com.trendyol.linkconverter.dto;

import com.trendyol.linkconverter.types.LinkType;
import lombok.Value;


@Value(staticConstructor = "of")
public class LinkDTO {
    String link;
    LinkType linkType;
}
