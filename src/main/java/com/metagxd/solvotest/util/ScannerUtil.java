package com.metagxd.solvotest.util;

import java.util.Scanner;

/**
 * This util class with metod for validating input from {@link Scanner}
 */
public class ScannerUtil {

    private ScannerUtil() {
    }

    /**
     * Validate input from {@link Scanner} for int
     * @param scanner scanner that will be use for {@link java.util.Scanner#nextInt}
     * @return scanned int
     */
    public static int scanInt(Scanner scanner) {
        System.out.println("Enter int:");
        while (!scanner.hasNextInt()) {
            System.out.println("That's not an int!");
            scanner.next();
        }
        return scanner.nextInt();
    }

    /**
     * Validate input from {@link Scanner} for {@link String}
     * @param scanner scanner that will be use for {@link java.util.Scanner#next(String pattern)}
     * @return scanned {@link String}
     */
    public static String getValidString(Scanner scanner) {
        System.out.println("Enter string length 1-32 symbols:");
        while (!scanner.hasNext("[a-zA-Z0-9_]{1,32}")) {
            System.out.println("That's not a string!");
            scanner.next();
        }
        return scanner.next();
    }

    /**
     * Validate input from {@link Scanner} for getting {@link com.metagxd.solvotest.model.Location} names
     * @param scanner scanner that will be use for {@link java.util.Scanner#next(String pattern)}
     * @return raw {@link String} that contains words split by ','
     */
    public static String getValidCellNames(Scanner scanner) {
        System.out.println("Enter names split by ',':");
        while (!scanner.hasNext("[a-zA-Z0-9_,]{1,32}")) {
            System.out.println("Incorrect input!");
            scanner.next();
        }
        return scanner.next();
    }

    /**
     * Validate input from {@link Scanner} and return string that contain .xml
     *
     * @param scanner {@link Scanner} that will be use for {@link java.util.Scanner#next(String pattern)}
     * @return {@link String} that contain '.xml'
     */
    public static String getValidFileName(Scanner scanner) {
        System.out.println("Enter filename:");
        while (!scanner.hasNext(".*\\.(xml)$")) {
            System.out.println("Incorrect input!");
            scanner.next();
        }
        return scanner.next();
    }
}
