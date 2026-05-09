package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        try {

            // Load SQLite JDBC Driver
            Class.forName("org.sqlite.JDBC");

            // Create Connection
            Connection con =
                    DriverManager.getConnection("jdbc:sqlite:movie.db");

            System.out.println("Database Connected Successfully");

            return con;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}