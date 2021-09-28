package com.metagxd.solvotest;

import com.metagxd.solvotest.model.Location;
import com.metagxd.solvotest.repository.LoadRepository;
import com.metagxd.solvotest.repository.LoadRepositoryImpl;
import com.metagxd.solvotest.repository.LocationRepository;
import com.metagxd.solvotest.repository.LocationRepositoryImpl;
import com.metagxd.solvotest.transfer.DataTransfer;
import com.metagxd.solvotest.transfer.LocationXMLTransfer;
import com.metagxd.solvotest.util.DbUtil;
import com.metagxd.solvotest.util.ScannerUtil;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final Map<Integer, Action> actions = new HashMap<>();

    static {
        String log4jConfPath = "src/main/resources/logger.properties";
        PropertyConfigurator.configure(log4jConfPath);
        DbUtil.initDb();
        for (Action action : Action.values()) {
            actions.put(action.getValue(), action);
        }
    }

    public static void main(String[] args) {
        Action action = Action.NONE;
        LocationRepository locationRepository = new LocationRepositoryImpl();

        while (action != Action.EXIT) {
            logger.info("Enter number of action and press enter \n {}", actions);
            int userInput = ScannerUtil.scanInt(scanner);
            action = actions.getOrDefault(userInput, Action.NONE);
            switch (action) {
                case EXIT:
                    break;
                case CREATE_LOADS:
                    LoadRepository loadRepository = new LoadRepositoryImpl();
                    logger.info("Enter quantity of loads for creation:");
                    int numOfLoads = ScannerUtil.scanInt(scanner);
                    logger.info("Enter name of cell:");
                    String nameOfCell = ScannerUtil.getValidString(scanner);
                    int numberOfCreated = loadRepository.create(numOfLoads, nameOfCell);
                    logger.info("{} loads created.", numberOfCreated);
                    action = Action.NONE;
                    break;
                case GET_INFO:
                    logger.info("Enter cell names [',' as separator]");
                    String rawCellNames = ScannerUtil.getValidCellNames(scanner);
                    String[] cellNames;
                    if (rawCellNames.contains(",")) {
                        cellNames = rawCellNames.split(",");
                    } else {
                        cellNames = new String[]{rawCellNames};
                    }
                    for (String name : cellNames) {
                        String cellName = name.trim();
                        int numOfLoadsInCell = locationRepository.countLoads(cellName);
                        System.out.println("| " + cellName + " | " + numOfLoadsInCell + " loads |");
                    }
                    break;
                case EXPORT:
                    logger.info("Input xml file name to transfer data");
                    String file = ScannerUtil.getValidFileName(scanner);
                    DataTransfer<Location> transfer = new LocationXMLTransfer();
                    transfer.saveToFile(file, locationRepository.getAllWithLoads());
                    break;
                default:
                    action = Action.NONE;
                    break;
            }
        }
    }

    public enum Action {
        EXIT(0),
        CREATE_LOADS(1),
        GET_INFO(2),
        EXPORT(3),
        NONE(4);

        private final int value;

        Action(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
