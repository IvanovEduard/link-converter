package com.trendyol.linkconverter.services.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Slf4j
@Component
public class HashGenerator {
    private static final String ALGORITHM_NAME_MD5 = "MD5";

    public Optional<String> generate(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM_NAME_MD5);
            md.update(value.getBytes());
            byte[] digest = md.digest();
            return Optional.of(DatatypeConverter
                    .printHexBinary(digest).toUpperCase());
        } catch (NoSuchAlgorithmException e) {
            log.error("The some problem with algorithm name, please check", e);
        }
        return Optional.empty();
    }
}
