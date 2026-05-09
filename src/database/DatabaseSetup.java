package database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    public static void main(String[] args) {

        try {

            Connection con = DBConnection.getConnection();

            Statement stmt = con.createStatement();

            // Users Table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT)"
            );

            // Movies Table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS movies (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "genre TEXT," +
                "rating REAL," +
                "year INTEGER," +
                "image TEXT)"
            );

            // History Table
            stmt.executeUpdate(
    "CREATE TABLE IF NOT EXISTS history (" +
    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
    "username TEXT," +
    "title TEXT," +
    "genre TEXT," +
    "rating REAL," +
    "year INTEGER," +
    "watched_time TEXT," +
    "image TEXT)"
);

            System.out.println("Tables Created Successfully");

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}