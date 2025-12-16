package com.military.app.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AesEncryptor {

    // ✅ FIXED SECRET KEY (16 characters = 128-bit AES)
    // In real systems → move this to application.properties or env variable
    private static final String SECRET_KEY = "MilitarySecret16";

    private static final SecretKeySpec KEY =
            new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

    // 🔐 Encrypt plain text
    public static String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, KEY);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    // 🔓 Decrypt encrypted text
    public static String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, KEY);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            return new String(cipher.doFinal(decodedBytes));
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}