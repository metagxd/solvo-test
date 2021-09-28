package com.metagxd.solvotest.util;

import java.util.Random;

/**
 * This utility class for generate random {@link String}
 */
public class StringGenerator {

    private StringGenerator() {
    }

    /**
     * Generate random {@link String} of letter a-z and digits 0-9
     * @param length of generated string.
     * @return generated string.
     */
    public static String getRandomString(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
