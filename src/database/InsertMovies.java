package database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertMovies {

    public static void main(String[] args) {

        try {

            Connection con = DBConnection.getConnection();

            String query =
                "INSERT INTO movies(title, genre, rating, year, image) VALUES(?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(query);

            // Inception
            ps.setString(1, "Inception");
            ps.setString(2, "Sci-Fi");
            ps.setDouble(3, 8.8);
            ps.setInt(4, 2010);
            ps.setString(5, "images/inception.jpg");
            ps.executeUpdate();

            // Interstellar
            ps.setString(1, "Interstellar");
            ps.setString(2, "Sci-Fi");
            ps.setDouble(3, 8.6);
            ps.setInt(4, 2014);
            ps.setString(5, "images/interstellar.jpg");
            ps.executeUpdate();

            // Kantara
            ps.setString(1, "Kantara");
            ps.setString(2, "Action");
            ps.setDouble(3, 8.3);
            ps.setInt(4, 2022);
            ps.setString(5, "images/kantara.jpg");
            ps.executeUpdate();

            // KGF Chapter 1
            ps.setString(1, "KGF Chapter 1");
            ps.setString(2, "Action");
            ps.setDouble(3, 8.2);
            ps.setInt(4, 2018);
            ps.setString(5, "images/kgf1.jpg");
            ps.executeUpdate();

            // KGF Chapter 2
            ps.setString(1, "KGF Chapter 2");
            ps.setString(2, "Action");
            ps.setDouble(3, 8.3);
            ps.setInt(4, 2022);
            ps.setString(5, "images/kgf2.jpg");
            ps.executeUpdate();

            // Vikram
            ps.setString(1, "Vikram");
            ps.setString(2, "Action");
            ps.setDouble(3, 8.3);
            ps.setInt(4, 2022);
            ps.setString(5, "images/vikram.jpg");
            ps.executeUpdate();

            // Kaithi
            ps.setString(1, "Kaithi");
            ps.setString(2, "Action");
            ps.setDouble(3, 8.5);
            ps.setInt(4, 2019);
            ps.setString(5, "images/kaithi.jpg");
            ps.executeUpdate();

            // Thuppakki
            ps.setString(1, "Thuppakki");
            ps.setString(2, "Action");
            ps.setDouble(3, 8.4);
            ps.setInt(4, 2012);
            ps.setString(5, "images/thuppakki.jpg");
            ps.executeUpdate();

            // Doctor
            ps.setString(1, "Doctor");
            ps.setString(2, "Comedy");
            ps.setDouble(3, 7.4);
            ps.setInt(4, 2021);
            ps.setString(5, "images/doctor.jpg");
            ps.executeUpdate();

            // Pushpa
            ps.setString(1, "Pushpa: The Rise");
            ps.setString(2, "Action");
            ps.setDouble(3, 7.6);
            ps.setInt(4, 2021);
            ps.setString(5, "images/pushpa.jpg");
            ps.executeUpdate();

            // RRR
            ps.setString(1, "RRR");
            ps.setString(2, "Action");
            ps.setDouble(3, 7.9);
            ps.setInt(4, 2022);
            ps.setString(5, "images/rrr.jpg");
            ps.executeUpdate();

            // Baahubali 2
            ps.setString(1, "Baahubali 2");
            ps.setString(2, "Action");
            ps.setDouble(3, 8.2);
            ps.setInt(4, 2017);
            ps.setString(5, "images/baahubali2.jpg");
            ps.executeUpdate();

            System.out.println("Movies Inserted Successfully");

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}