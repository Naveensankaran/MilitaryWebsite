package com.military.app.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hash(String input) {
        return encoder.encode(input);
    }

    public static boolean match(String raw, String hashed) {
        return encoder.matches(raw, hashed);
    }
}
