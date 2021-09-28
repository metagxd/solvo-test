package com.metagxd.solvotest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ScannerUtil {

    private ScannerUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ScannerUtil.class);

    public static int scanInt(Scanner scanner) {
        logger.info("Enter int:");
        while (!scanner.hasNextInt()) {
            logger.error("That's not an int!");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public static String getValidString(Scanner scanner) {
        logger.info("Enter string length 1-32 symbols:");
        while (!scanner.hasNext("[a-zA-Z0-9_]{1,32}")) {
            logger.error("That's not a string!");
            scanner.next();
        }
        return scanner.next();
    }

    public static String getValidCellNames(Scanner scanner) {
        logger.info("Enter names split by ',':");
        while (!scanner.hasNext("[a-zA-Z0-9_,]{1,32}")) {
            logger.error("Incorrect input!");
            scanner.next();
        }
        return scanner.next();
    }

    public static String getValidFileName(Scanner scanner) {
        logger.info("Enter filename:");
        while (!scanner.hasNext(".*\\.(xml)$")) {
            logger.error("Incorrect input!");
            scanner.next();
        }
        return scanner.next();
    }
}
