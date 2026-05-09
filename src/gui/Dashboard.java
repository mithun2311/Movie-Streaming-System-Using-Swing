package gui;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;

public class Dashboard extends JFrame {

    JPanel moviesPanel;

    JButton historyButton;
    JButton logoutButton;

    JButton sortRatingButton;
    JButton sortYearButton;
    JButton filterButton;

    String loggedInUser;

    ArrayList<HistoryMovie> movieList =
            new ArrayList<>();

    public Dashboard(String username) {

        loggedInUser = username;

        setTitle("Movie Dashboard");

        setSize(1100, 750);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel();

        historyButton =
                new JButton("Watch History");

        sortRatingButton =
                new JButton("Sort by Rating");

        sortYearButton =
                new JButton("Sort by Year");

        filterButton =
                new JButton("Rating > 8");

        logoutButton =
                new JButton("Logout");

        topPanel.add(historyButton);

        topPanel.add(sortRatingButton);

        topPanel.add(sortYearButton);

        topPanel.add(filterButton);

        topPanel.add(logoutButton);

        add(topPanel, BorderLayout.NORTH);

        // Movies Panel
        moviesPanel = new JPanel();

        moviesPanel.setLayout(
                new GridLayout(0, 3, 20, 20)
        );

        moviesPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        JScrollPane scrollPane =
                new JScrollPane(moviesPanel);

        add(scrollPane, BorderLayout.CENTER);

        // Open Watch History
        historyButton.addActionListener(e -> {

            new WatchHistoryFrame(loggedInUser);
        });

        // Sort by Rating
        sortRatingButton.addActionListener(e -> {

            moviesPanel.removeAll();

            movieList.sort(
                    HistoryMovie.ratingComparator
            );

            displayMovies(movieList);

            moviesPanel.revalidate();

            moviesPanel.repaint();
        });

        // Sort by Year
        sortYearButton.addActionListener(e -> {

            moviesPanel.removeAll();

            movieList.sort(
                    HistoryMovie.yearComparator
            );

            displayMovies(movieList);

            moviesPanel.revalidate();

            moviesPanel.repaint();
        });

        // Filter Rating > 8
        filterButton.addActionListener(e -> {

            moviesPanel.removeAll();

            ArrayList<HistoryMovie> filteredList =
                    new ArrayList<>();

            for (HistoryMovie movie : movieList) {

                if (movie.rating > 8.0) {

                    filteredList.add(movie);
                }
            }

            displayMovies(filteredList);

            moviesPanel.revalidate();

            moviesPanel.repaint();
        });

        // Logout
        logoutButton.addActionListener(e -> {

            dispose();

            new LoginFrame();
        });

        // Load Movies
        loadMovies();

        // Default Display
        displayMovies(movieList);

        setVisible(true);
    }

    // Load Movies from Database
    public void loadMovies() {

        try {

            Connection con =
                    DBConnection.getConnection();

            Statement stmt =
                    con.createStatement();

            ResultSet rs =
                    stmt.executeQuery(
                            "SELECT * FROM movies"
                    );

            while (rs.next()) {

                String title =
                        rs.getString("title");

                String genre =
                        rs.getString("genre");

                double rating =
                        rs.getDouble("rating");

                int year =
                        rs.getInt("year");

                String imagePath =
                        rs.getString("image");

                HistoryMovie movie =
                        new HistoryMovie(
                                title,
                                genre,
                                rating,
                                year,
                                "Not Watched Yet",
                                imagePath
                        );

                movieList.add(movie);
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // Display Movies
    public void displayMovies(
            ArrayList<HistoryMovie> movies
    ) {

        for (HistoryMovie movie : movies) {

            JPanel card = new JPanel();

            card.setLayout(
                    new BoxLayout(card, BoxLayout.Y_AXIS)
            );

            card.setBorder(
                    BorderFactory.createLineBorder(Color.GRAY)
            );

            // Movie Poster
            ImageIcon icon =
                    new ImageIcon(movie.imagePath);

            Image image =
                    icon.getImage().getScaledInstance(
                            200,
                            250,
                            Image.SCALE_SMOOTH
                    );

            JLabel imageLabel =
                    new JLabel(new ImageIcon(image));

            JLabel titleLabel =
                    new JLabel(movie.title);

            JLabel genreLabel =
                    new JLabel("Genre: " + movie.genre);

            JLabel ratingLabel =
                    new JLabel("Rating: " + movie.rating);

            JLabel yearLabel =
                    new JLabel("Year: " + movie.year);

            JButton watchButton =
                    new JButton("Watch");

            // Watch Button Logic
            watchButton.addActionListener(e -> {

                try {

                    Connection historyCon =
                            DBConnection.getConnection();

                    String watchedTime =
                            java.time.LocalDateTime.now()
                            .format(
                                    java.time.format.DateTimeFormatter
                                            .ofPattern("dd-MM-yyyy HH:mm:ss")
                            );

                    // Check Existing
                    String checkQuery =
                            "SELECT * FROM history " +
                            "WHERE username=? AND title=?";

                    PreparedStatement checkPs =
                            historyCon.prepareStatement(checkQuery);

                    checkPs.setString(1, loggedInUser);

                    checkPs.setString(2, movie.title);

                    ResultSet crs =
                            checkPs.executeQuery();

                    // Update Existing
                    if (crs.next()) {

                        String updateQuery =
                                "UPDATE history SET watched_time=? " +
                                "WHERE username=? AND title=?";

                        PreparedStatement updatePs =
                                historyCon.prepareStatement(updateQuery);

                        updatePs.setString(1, watchedTime);

                        updatePs.setString(2, loggedInUser);

                        updatePs.setString(3, movie.title);

                        updatePs.executeUpdate();

                        JOptionPane.showMessageDialog(
                                null,
                                movie.title + " watch time updated"
                        );

                    }

                    // Insert New
                    else {

                        String insertQuery =
                                "INSERT INTO history(username, title, genre, rating, year, watched_time, image) VALUES(?,?,?,?,?,?,?)";

                        PreparedStatement insertPs =
                                historyCon.prepareStatement(insertQuery);

                        insertPs.setString(1, loggedInUser);

                        insertPs.setString(2, movie.title);

                        insertPs.setString(3, movie.genre);

                        insertPs.setDouble(4, movie.rating);

                        insertPs.setInt(5, movie.year);

                        insertPs.setString(6, watchedTime);

                        insertPs.setString(7, movie.imagePath);

                        insertPs.executeUpdate();

                        JOptionPane.showMessageDialog(
                                null,
                                movie.title + " added to history"
                        );
                    }

                    historyCon.close();

                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            });

            // Alignment
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            genreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            ratingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            yearLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            watchButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Add Components
            card.add(Box.createVerticalStrut(10));

            card.add(imageLabel);

            card.add(Box.createVerticalStrut(10));

            card.add(titleLabel);

            card.add(genreLabel);

            card.add(ratingLabel);

            card.add(yearLabel);

            card.add(Box.createVerticalStrut(10));

            card.add(watchButton);

            card.add(Box.createVerticalStrut(10));

            moviesPanel.add(card);
        }
    }
}