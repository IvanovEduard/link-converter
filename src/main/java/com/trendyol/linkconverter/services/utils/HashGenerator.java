package com.trendyol.linkconverter.services.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * The main responsibility of this component is generating the <b>hash</b> by input string.
 * The used algorithm you can find in <b>source.properties#app.algorithm.name</b>
 */
@Slf4j
@Component
public class HashGenerator {

    @Value("${app.algorithm.name}")
    private String algorithmName;

    /**
     * Method intended for generation <b>hash</b> for input string.
     *
     * @param value the any string for which need generate <b>hash</b>
     * @return hex binary <b>hash</b>
     */
    public Optional<String> generate(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithmName);
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
