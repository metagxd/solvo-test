package com.metagxd.solvotest.repository;

import com.metagxd.solvotest.model.Load;
import com.metagxd.solvotest.util.DbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class LoadRepositoryImpl implements LoadRepository {

    private static final Logger logger = LoggerFactory.getLogger(LoadRepositoryImpl.class);

    private final LocationRepository locationRepository = new LocationRepositoryImpl();

    @Override
    public int create(int quantity, String cellName) {
        logger.debug("Creating {} loads in cell {}", quantity, cellName);

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Loads (name, Loc_id) VALUES (?, (SELECT id FROM Location WHERE name = ?))"
             )) {

            if (locationRepository.createIfNotExist(cellName)) {
                logger.info("Cell {} created", cellName);
            }

            for (int i = 0; i < quantity; i++) {
                Load load = new Load();
                preparedStatement.setString(1, load.getName());
                preparedStatement.setString(2, cellName);
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
