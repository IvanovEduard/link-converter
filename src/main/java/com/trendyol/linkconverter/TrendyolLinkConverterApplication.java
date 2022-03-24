package com.trendyol.linkconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:source.properties")
public class TrendyolLinkConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrendyolLinkConverterApplication.class, args);
    }
}
