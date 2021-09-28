package com.metagxd.solvotest;

import com.metagxd.solvotest.exception.DBException;
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

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    static {
        PropertyConfigurator.configure("logger.properties");
        try {
            DbUtil.initDb();
        } catch (SQLException sqlException) {
            throw new DBException("Can't connect to database ", sqlException);
        }
    }

    public static void main(String[] args) {
        Action userAction = Action.NONE;
        LocationRepository locationRepository = new LocationRepositoryImpl();
        LoadRepository loadRepository = new LoadRepositoryImpl();
        Map<Integer, Action> actions = new HashMap<>();
        for (Action action : Action.values()) {
            actions.put(action.getValue(), action);
        }

        while (userAction != Action.EXIT) {
            System.out.println("Enter number of action and press enter \n" + actions);
            int userInput = ScannerUtil.scanInt(scanner);
            userAction = actions.getOrDefault(userInput, Action.NONE);
            switch (userAction) {
                case EXIT:
                    break;
                case CREATE_LOADS:
                    System.out.println("Enter quantity of loads for creation:");
                    int numOfLoads = ScannerUtil.scanInt(scanner);
                    System.out.println("Enter name of cell:");
                    String cellName = ScannerUtil.getValidString(scanner);
                    locationRepository.createIfNotExist(cellName);
                    int numberOfCreated = loadRepository.create(numOfLoads, cellName);
                    System.out.println(numberOfCreated + " loads created.");
                    userAction = Action.NONE;
                    break;
                case GET_INFO:
                    System.out.println("Enter cell names [',' as separator]");
                    String rawCellNames = ScannerUtil.getValidCellNames(scanner);
                    String[] cellNames;
                    if (rawCellNames.contains(",")) {
                        cellNames = rawCellNames.split(",");
                    } else {
                        cellNames = new String[]{rawCellNames};
                    }
                    for (String name : cellNames) {
                        String cell = name.trim();
                        int numOfLoadsInCell = locationRepository.countLoads(cell);
                        System.out.println("| " + cell + " | " + numOfLoadsInCell + " loads |");
                    }
                    break;
                case EXPORT:
                    System.out.println("Enter xml file name to transfer data");
                    String file = ScannerUtil.getValidFileName(scanner);
                    DataTransfer<Location> transfer = new LocationXMLTransfer();
                    try {
                        transfer.saveToFile(file, locationRepository.getAllWithLoads());
                    } catch (Exception e) {
                        System.out.println("Can't transfer to file " + e.getMessage());
                    }
                    break;
                default:
                    userAction = Action.NONE;
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
