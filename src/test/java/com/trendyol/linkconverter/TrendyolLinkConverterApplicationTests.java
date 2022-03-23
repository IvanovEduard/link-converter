package com.trendyol.linkconverter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class TrendyolLinkConverterApplicationTests {

    @Test
    void contextLoads() throws NoSuchAlgorithmException {
        String stringToHash = "asdf";
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(stringToHash.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        System.out.println(myHash);
    }

}
//D41D8CD98F00B204E9800998ECF8427E
//D41D8CD98F00B204E9800998ECF8427E
//1F5364C58947E14F9AFA445BDF1BA4D9
//912EC803B2CE49E4A541068D495AB570
//86F9DF3B0712D72948E1E8303A5ACEAC
//E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855
//E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855