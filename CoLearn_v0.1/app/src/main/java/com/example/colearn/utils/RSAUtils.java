package com.example.colearn.utils;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class RSAUtils {

    public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCi2xw1kR0inizbC3bV8F7q1fsvHBZw0b21W++wOvsG6btD9WjhXqUcU4QihF7qO8Por2I7MCeCuGLcFbjn3k/QDFxT7CFkHvFZ2Oa34FhjQofHhVawx3LinVXFwt+TA5jgmGIoqpUsWvu9wELFiQyWy1tl8Vzly7zgppbGQ5XsTwIDAQAB";
    private static final String RSA_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static final Charset UTF8 = StandardCharsets.UTF_8;

    public static String encrypt(String encryptedStr) {
        try {
            RSAPublicKey rsaPublicKey = loadPublicKey();
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey, new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));
            cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
            return Base64.encodeBase64String(cipher.doFinal(encryptedStr.getBytes(UTF8)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static RSAPublicKey loadPublicKey() throws Exception {
        try {
            byte[] decodedPubKey = Base64.decodeBase64(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedPubKey);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
