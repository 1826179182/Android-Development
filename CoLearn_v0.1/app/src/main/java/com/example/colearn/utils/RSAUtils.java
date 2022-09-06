package com.example.colearn.utils;

import org.apache.commons.codec.binary.Base64;

import org.apache.commons.io.FileUtils;
//import sun.misc.BASE64Decoder;
//import java.util.Base64;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;


//public class RSA_Util {
//
//    private static final String RSA_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
//    private static final Charset UTF8 = Charset.forName("UTF-8");
//
//    static {
//        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//    }
//
//    /**
//     * 加密文件
//     *
//     * @param keyPath 公钥路径，DER
//     * @param input   输入文件地址
//     * @param output  输出文件地址
//     */
//    public static boolean encrypt(String keyPath, String input, String output) {
//        FileInputStream fileInputStream = null;
//        FileOutputStream fileOutputStream = null;
//        List<String> list = new ArrayList();
//        try {
//            byte[] buffer = Files.readAllBytes(Paths.get(keyPath));
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            PublicKey publicKey = keyFactory.generatePublic(keySpec);
//
//            File inputFile = new File(input);
//            File outputFile = new File(output);
//            fileInputStream = new FileInputStream(inputFile);
//            fileOutputStream = new FileOutputStream(outputFile);
//            byte[] inputByte = new byte[116];
//            int len;
//            while ((len = fileInputStream.read(inputByte)) != -1) {
//                list.add(new String(inputByte, 0, len));
//            }
//            for (String s : list) {
//                byte[] encrypted = encrypt(publicKey, s);
//                fileOutputStream.write(encrypted);
//                fileOutputStream.flush();
//            }
//            fileOutputStream.close();
//            fileInputStream.close();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    private static byte[] encrypt(PublicKey publicKey, String message) throws Exception {
//        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM, "BC");
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey, new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        return Base64.encodeBase64(cipher.doFinal(message.getBytes(UTF8)));
//    }
//
//    /**
//     * 从字符串中加载公钥
//     */
//    private static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
//        try {
//            byte[] buffer = new BASE64Decoder().decodeBuffer(publicKeyStr);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
//            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        } catch (InvalidKeySpecException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    public static void main(String[] args) throws Exception {
//        encrypt("F:\\dls\\chunk\\config\\rsa_public_key.der", "C:\\Users\\Carol\\Desktop\\file\\868663032830438_migu$user$login$_1554947856915.log", "F:\\868663032830438_migu$user$login$_1554947856915.log");
//    }
//
//}


public class RSAUtils {
    private static final String RSA_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCi2xw1kR0inizbC3bV8F7q1fsvHBZw0b21W++wOvsG6btD9WjhXqUcU4QihF7qO8Por2I7MCeCuGLcFbjn3k/QDFxT7CFkHvFZ2Oa34FhjQofHhVawx3LinVXFwt+TA5jgmGIoqpUsWvu9wELFiQyWy1tl8Vzly7zgppbGQ5XsTwIDAQAB";
    public static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKLbHDWRHSKeLNsLdtXwXurV+y8cFnDRvbVb77A6+wbpu0P1aOFepRxThCKEXuo7w+ivYjswJ4K4YtwVuOfeT9AMXFPsIWQe8VnY5rfgWGNCh8eFVrDHcuKdVcXC35MDmOCYYiiqlSxa+73AQsWJDJbLW2XxXOXLvOCmlsZDlexPAgMBAAECgYAJAxcMins87y6MRet4QKpVVIIR7qakl6Fn59kxEhJ8z3Ji0Fx9cZSxfJhTUKqaoQlhAajt8ubnf6H44TraRJ3QpDgjhS5kpLAQxytEHcgVTJfQWNuo5QM8X6b6p+celFWJO4LI8r27HcAm4iqIMZm24uaQOIP1spe41UVApvhUAQJBANpnUpJQiOhTlbST/Q6mbjJkNbDBn/61AcBljiLOedXPPmMa6vAbqjvCdWk7xguNN1A+eQMjLzMT5mHg3fMsjLsCQQC+4+Ttoju/shYUCaaan0rx2T6VPZnFoGpyiXdT5Phe0JT1LtUOVSRSwf2imRcaukGaKeGzk9Dc+79f85tnfs99AkAC5+0JVVj8/l+3j2CGmWBN+1dbirf+Q5pKXQ+PFwA4ABHsb6dAmp2hPe0iAVGZRb++qZYinI08OL+P/dX2LlmRAkEAkoMeCZo0W7yXH4gMvK64egnw8+EU7k9ihEioMtcSHsvfGJR6aIbG21ITl0IDLrt4cJfmqMsySvpad43lYDk/jQJBAJ0UGv6VHkXpZETh2vZq+zMACFWLLFYXGURJBxGsyEiQOod0u/pqhBVs4zSvhRc674bRnNAM3TD3ibu8XaWAyNY=";




    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt(String str) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey= (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RAS加密
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM,"BC");
        cipher.init(Cipher.ENCRYPT_MODE,pubKey);
        String outStr=Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str) throws Exception {
        //Base64解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //Base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(new X509EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,priKey);
        String outStr=new String(cipher.doFinal(inputByte));
        return outStr;
    }
}

