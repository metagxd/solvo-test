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

    private static final Logger logger = LoggerFactory.getLogger(LoadRepositoryImpl.class);

    private final ConnectionFactory connectionFactory = new SQLiteConnectionFactory();
    private final LocationRepository locationRepository = new LocationRepositoryImpl();

    @Override
    public int create(int quantity, String cellName) {
        logger.debug("Creating {} loads in cell {}", quantity, cellName);

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
            logger.error("An error occurred ", sqlException);
        }
        return 0;
    }
}
