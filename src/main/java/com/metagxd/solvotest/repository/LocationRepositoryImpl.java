package com.metagxd.solvotest.repository;

import com.metagxd.solvotest.db.ConnectionFactory;
import com.metagxd.solvotest.db.SQLiteConnectionFactory;
import com.metagxd.solvotest.model.Load;
import com.metagxd.solvotest.model.Location;

import java.sql.*;
import java.util.*;

public class LocationRepositoryImpl implements LocationRepository {

    private final ConnectionFactory connectionFactory = new SQLiteConnectionFactory();

    @Override
    public Location createIfNotExist(String cellName) {
        try (Connection connection = connectionFactory.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Location (name) VALUES (?) ON CONFLICT DO NOTHING ")) {
            preparedStatement.setString(1, cellName);
            if (preparedStatement.execute()) {
                int id = preparedStatement.getGeneratedKeys().getInt(1);
                return new Location(id, cellName);
            }
            final PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT id FROM Location WHERE name = ?");
            preparedStatement1.setString(1, cellName);
            final ResultSet resultSet = preparedStatement1.executeQuery();
            int id = resultSet.getInt(1);
            return new Location(id, cellName);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public int countLoads(String locationName) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT count(ld.id) FROM Location loc LEFT JOIN Loads ld " +
                             "ON loc.id = ld.Loc_id WHERE loc.name = (?)")) {
            preparedStatement.setString(1, locationName);
            return preparedStatement.executeQuery().getInt(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }

    public List<Location> getAll() {
        List<Location> locationList = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Location");
            while (resultSet.next()) {
                locationList.add(new Location(resultSet.getInt(1), resultSet.getString(2)));
            }
            return locationList;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Location> getAllWithLoads() {
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
            sqlException.printStackTrace();
        }
        locationLoadMap.forEach(Location::setLoadList);
        return new ArrayList<>(locationLoadMap.keySet());
    }
}
