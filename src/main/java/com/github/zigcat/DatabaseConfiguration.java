package com.github.zigcat;

import com.github.zigcat.ormlite.models.Exercise;
import com.github.zigcat.ormlite.models.Food;
import com.github.zigcat.ormlite.models.User;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseConfiguration {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fitnesshelper?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DB_LOGIN = "root";
    private static final String DB_PASSWORD = "";
    public static JdbcPooledConnectionSource source;

    static {
        try {
            source = new JdbcPooledConnectionSource(DB_URL, DB_LOGIN, DB_PASSWORD);
            TableUtils.createTableIfNotExists(source, User.class);
            TableUtils.createTableIfNotExists(source, Food.class);
            TableUtils.createTableIfNotExists(source, Exercise.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
