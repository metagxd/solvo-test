package com.metagxd.solvotest.repository;

import com.metagxd.solvotest.model.Load;
import com.metagxd.solvotest.model.Location;
import com.metagxd.solvotest.util.DbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationRepositoryImpl implements LocationRepository {

    private static final Logger logger = LoggerFactory.getLogger(LocationRepositoryImpl.class);

    /**
     *Create a location in database if it doesn't exist.
     *
     * @param locationName name of location to create
     * @return true if cell was created and false if not
     */
    @Override
    public boolean createIfNotExist(String locationName) {
        logger.debug("Creating cell with name {}", locationName);
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Location (name) VALUES (?) ON CONFLICT DO NOTHING ")) {
            preparedStatement.setString(1, locationName);
            return preparedStatement.execute();
        } catch (SQLException sqlException) {
            logger.error("An error occurred", sqlException);
        }
        return false;
    }

    /**
     * Counting loads in database that refer to locationName.
     *
     * @param locationName name of location
     * @return number of loads for location
     */
    public int countLoads(String locationName) {
        logger.debug("Counting loads in cell {}", locationName);
        try (Connection connection = DbUtil.getConnection();
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

    /**
     * Get all locations with their loads
     *
     * @return list of location
     */
    public List<Location> getAllWithLoads() {
        logger.debug("Getting all locations.");
        Map<Location, List<Load>> locationLoadMap = new HashMap<>();
        try (Connection connection = DbUtil.getConnection();
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
        locationLoadMap.forEach(Location::setLoads);
        return new ArrayList<>(locationLoadMap.keySet());
    }
}
