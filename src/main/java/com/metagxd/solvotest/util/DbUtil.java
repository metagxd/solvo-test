package com.metagxd.solvotest.util;

import com.metagxd.solvotest.exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {

    private DbUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(DbUtil.class);
    private static final String JDBC_URL = "jdbc:sqlite:test.db";

    /**
     * Initialize DB at startup of app, do nothing if table already exist.
     *
     * @throws SQLException when can't execute query
     */
    public static void initDb() throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Location\n" +
                    "(\n" +
                    "    id INTEGER\n" +
                    "        CONSTRAINT Location_pk\n" +
                    "            PRIMARY KEY ,\n" +
                    "    name VARCHAR(32) UNIQUE NOT NULL\n" +
                    ");");
            statement.execute("CREATE TABLE IF NOT EXISTS Loads\n" +
                    "(\n" +
                    "    id INTEGER\n" +
                    "        CONSTRAINT Loads_pk\n" +
                    "            PRIMARY KEY ,\n" +
                    "    name VARCHAR(32) UNIQUE NOT NULL,\n" +
                    "    Loc_id INTEGER\n" +
                    "        REFERENCES Location\n" +
                    ");");
            statement.execute("CREATE UNIQUE INDEX IF NOT EXISTS Loads_name_uindex\n" +
                    "    ON Loads (name);");
        } catch (SQLException sqlException) {
            logger.error("An error occurred", sqlException);
            throw new DBException("Can't init database ", sqlException);
        }
    }

    /**
     * Attempts to establish a connection to the database.
     *
     * @return Connection to database.
     * @throws SQLException when can't create connection for any reason.
     */
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(JDBC_URL);
        } catch (SQLException sqlException) {
            logger.error("Error getting connection");
            throw sqlException;
        }
    }
}
