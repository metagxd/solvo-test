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

    /**
     * Create loads with random name if generated name already exist in database loads won't be created.
     *
     * @param quantity     quantity of loads to create
     * @param locationName name of cell where load will store
     * @return number of created rows in database
     */
    @Override
    public int create(int quantity, String locationName) {
        logger.debug("Creating {} loads in cell {}", quantity, locationName);

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Loads (name, Loc_id) VALUES (?, (SELECT id FROM Location WHERE name = ?)) " +
                             "ON CONFLICT DO NOTHING"
             )) {

            for (int i = 0; i < quantity; i++) {
                Load load = new Load();
                preparedStatement.setString(1, load.getName());
                preparedStatement.setString(2, locationName);
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
