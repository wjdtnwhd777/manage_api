package com.example.manage.core.encryption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
public class Crypt {
    private static final String SECRET_KEY = "KMONGSECRETKEY1!";
    private static final String ALGO = "AES"; // Default uses ECB PKCS5Padding

    public static String encrypt(final String plainText) throws Exception {
        try {
            Key key = generateKey(encodeKey(SECRET_KEY));
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encVal);
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new Exception("encrypt fail");
        }
    }

    public static String decrypt(final String encryptedText) throws Exception {
        try {
            Key key = generateKey(encodeKey(SECRET_KEY));
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
        }
        catch(Exception e) {
            log.error(e.getLocalizedMessage());
            throw new Exception("decrypt fail");
        }
    }

    private static Key generateKey(String secret) {
        byte[] decoded = Base64.getDecoder().decode(secret.getBytes());
        return new SecretKeySpec(decoded, ALGO);
    }

    private static String decodeKey(String str) {
        byte[] decoded = Base64.getDecoder().decode(str.getBytes());
        return new String(decoded);
    }

    public static String encodeKey(String str) {
        byte[] encoded = Base64.getEncoder().encode(str.getBytes());
        return new String(encoded);
    }
}