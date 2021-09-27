package com.metagxd.solvotest.repository;

import com.metagxd.solvotest.db.ConnectionFactory;
import com.metagxd.solvotest.db.SQLiteConnectionFactory;
import com.metagxd.solvotest.model.Load;
import com.metagxd.solvotest.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class LoadRepositoryImpl implements LoadRepository {

    private final ConnectionFactory connectionFactory = new SQLiteConnectionFactory();
    private final LocationRepository locationRepository = new LocationRepositoryImpl();

    private static Logger logger = LoggerFactory.getLogger(LoadRepositoryImpl.class);

    @Override
    public Load create() {
        return null;
    }

    @Override
    public int createMany(int quantity, String cellName) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Loads (name, Loc_id) VALUES (?, ?)"
             )) {

            Location location = locationRepository.createIfNotExist(cellName);

            for (int i = 0; i < quantity; i++) {
                Load load = new Load();
                preparedStatement.setString(1, load.getName());
                preparedStatement.setInt(2, location.getId());
                preparedStatement.addBatch();
            }
            int[] ints = preparedStatement.executeBatch();
            return Arrays.stream(ints).sum();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }
}
