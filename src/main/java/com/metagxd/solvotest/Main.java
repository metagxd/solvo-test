package com.metagxd.solvotest;

import com.metagxd.solvotest.db.ConnectionFactory;
import com.metagxd.solvotest.db.SQLiteConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        ConnectionFactory connectionFactory = new SQLiteConnectionFactory();
        try (Connection connection = connectionFactory.getConnection()) {
        }
    }
}
