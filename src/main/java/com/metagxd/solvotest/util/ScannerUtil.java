package com.metagxd.solvotest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerUtil {
    private static final Logger logger = LoggerFactory.getLogger(ScannerUtil.class);

    public static int scanInt(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            logger.error("Invalid input, enter number");
        }
        return -1;
    }
}
