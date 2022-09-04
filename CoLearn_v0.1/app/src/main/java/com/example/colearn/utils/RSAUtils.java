package com.example.colearn.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


public class RSAUtils {
    private static final String RSA_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCi2xw1kR0inizbC3bV8F7q1fsvHBZw0b21W++wOvsG6btD9WjhXqUcU4QihF7qO8Por2I7MCeCuGLcFbjn3k/QDFxT7CFkHvFZ2Oa34FhjQofHhVawx3LinVXFwt+TA5jgmGIoqpUsWvu9wELFiQyWy1tl8Vzly7zgppbGQ5XsTwIDAQAB";
    public static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKLbHDWRHSKeLNsLdtXwXurV+y8cFnDRvbVb77A6+wbpu0P1aOFepRxThCKEXuo7w+ivYjswJ4K4YtwVuOfeT9AMXFPsIWQe8VnY5rfgWGNCh8eFVrDHcuKdVcXC35MDmOCYYiiqlSxa+73AQsWJDJbLW2XxXOXLvOCmlsZDlexPAgMBAAECgYAJAxcMins87y6MRet4QKpVVIIR7qakl6Fn59kxEhJ8z3Ji0Fx9cZSxfJhTUKqaoQlhAajt8ubnf6H44TraRJ3QpDgjhS5kpLAQxytEHcgVTJfQWNuo5QM8X6b6p+celFWJO4LI8r27HcAm4iqIMZm24uaQOIP1spe41UVApvhUAQJBANpnUpJQiOhTlbST/Q6mbjJkNbDBn/61AcBljiLOedXPPmMa6vAbqjvCdWk7xguNN1A+eQMjLzMT5mHg3fMsjLsCQQC+4+Ttoju/shYUCaaan0rx2T6VPZnFoGpyiXdT5Phe0JT1LtUOVSRSwf2imRcaukGaKeGzk9Dc+79f85tnfs99AkAC5+0JVVj8/l+3j2CGmWBN+1dbirf+Q5pKXQ+PFwA4ABHsb6dAmp2hPe0iAVGZRb++qZYinI08OL+P/dX2LlmRAkEAkoMeCZo0W7yXH4gMvK64egnw8+EU7k9ihEioMtcSHsvfGJR6aIbG21ITl0IDLrt4cJfmqMsySvpad43lYDk/jQJBAJ0UGv6VHkXpZETh2vZq+zMACFWLLFYXGURJBxGsyEiQOod0u/pqhBVs4zSvhRc674bRnNAM3TD3ibu8XaWAyNY=";

    public static String encrypt(String str) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RAS加密
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    public static String decrypt(String str) throws Exception {
        //Base64解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //Base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(new X509EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}

