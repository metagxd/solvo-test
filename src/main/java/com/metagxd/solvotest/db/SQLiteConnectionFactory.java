package com.metagxd.solvotest.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnectionFactory implements ConnectionFactory {

    private static final Logger logger = LoggerFactory.getLogger(SQLiteConnectionFactory.class);

    private static final String JDBC_DRIVER_NAME = "jdbc:sqlite:test.db";

    @Override
    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(JDBC_DRIVER_NAME);
        } catch (SQLException sqlException) {
            logger.error("Error getting connection");
            throw sqlException;
        }
    }
}
