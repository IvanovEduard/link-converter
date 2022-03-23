package com.trendyol.linkconverter.dto;


import com.trendyol.linkconverter.services.validation.ValidateLink;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputLinkDataDTO {
    @ValidateLink()
    String link;
}
