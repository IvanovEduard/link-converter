package com.trendyol.linkconverter.services.utils;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.spy;

public class HashGeneratorTest {
    private final HashGenerator hashGenerator = spy(HashGenerator.class);

    private final static String WEB_LINK_PRODUCT = "https://www.trendyol.com/casio/saat-p-1925865?boutiqueId=439892&merchantId=105064";


    @Test
    public void testHashGenerate() {
        assertThat(hashGenerator.generate(WEB_LINK_PRODUCT), is(Optional.of("D1096DAABE7B7DF327140B5770002FA0")));
    }
}