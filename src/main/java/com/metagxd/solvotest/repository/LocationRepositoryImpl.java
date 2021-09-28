package com.metagxd.solvotest.repository;

import com.metagxd.solvotest.db.ConnectionFactory;
import com.metagxd.solvotest.db.SQLiteConnectionFactory;
import com.metagxd.solvotest.model.Load;
import com.metagxd.solvotest.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class LocationRepositoryImpl implements LocationRepository {

    private static final Logger logger = LoggerFactory.getLogger(LocationRepositoryImpl.class);
    private static final ConnectionFactory connectionFactory = new SQLiteConnectionFactory();

    @Override
    public boolean createIfNotExist(String cellName) {
        logger.debug("Creating cell with name {}", cellName);
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Location (name) VALUES (?) ON CONFLICT DO NOTHING ")) {
            preparedStatement.setString(1, cellName);
            return preparedStatement.execute();
        } catch (SQLException sqlException) {
            logger.error("An error occurred", sqlException);
        }
        return false;
    }

    public int countLoads(String locationName) {
        logger.debug("Counting loads in cell {}", locationName);
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT count(ld.id) FROM Location loc LEFT JOIN Loads ld " +
                             "ON loc.id = ld.Loc_id WHERE loc.name = (?)")) {
            preparedStatement.setString(1, locationName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            logger.error("An error occurred", sqlException);
        }
        return 0;
    }

    public List<Location> getAllWithLoads() {
        logger.debug("Getting all locations.");
        Map<Location, List<Load>> locationLoadMap = new HashMap<>();
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Location loc LEFT JOIN Loads lod on loc.id = lod.Loc_id")) {
            while (resultSet.next()) {
                int locationId = resultSet.getInt(1);
                String locationName = resultSet.getString(2);
                Location location = new Location(locationId, locationName);
                int loadId = resultSet.getInt(3);
                String loadName = resultSet.getString(4);
                if (loadName != null) {
                    Load load = new Load(loadId, loadName);
                    locationLoadMap.merge(location, new ArrayList<>(Collections.singletonList(load)), (loads, loads2) -> {
                        loads.add(loads2.get(0));
                        return loads;
                    });
                }
            }
        } catch (SQLException sqlException) {
            logger.error("An error occurred", sqlException);
        }
        //merge Locations with load list
        locationLoadMap.forEach(Location::setLoadList);
        return new ArrayList<>(locationLoadMap.keySet());
    }
}
