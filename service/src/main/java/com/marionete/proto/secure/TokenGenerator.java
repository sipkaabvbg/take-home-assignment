package com.marionete.proto.secure;

import java.security.SecureRandom;

/**
 * Generate token
 */
public class TokenGenerator {
    public static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static final int SECURE_TOKEN_LENGTH = 256;

    private static final char[] symbols = CHARACTERS.toCharArray();

    private static final char[] buffer = new char[SECURE_TOKEN_LENGTH];

    private static final SecureRandom random = new SecureRandom();
    /**
     *
     * Generate the secure random token.
     */
    public static String getToken() {
        for (int i = 0; i < buffer.length; ++i)
            buffer[i] = symbols[random.nextInt(symbols.length)];
        return new String(buffer);
    }
}