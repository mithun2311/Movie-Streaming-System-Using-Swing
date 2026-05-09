package gui;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;

public class WatchHistoryFrame extends JFrame {

    JPanel historyPanel;

    JButton sortRatingButton;
    JButton sortYearButton;
    JButton filterButton;

    String loggedInUser;

    ArrayList<HistoryMovie> historyList =
            new ArrayList<>();

    public WatchHistoryFrame(String username) {

        loggedInUser = username;

        setTitle("Watch History");

        setSize(1000, 700);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel();

        sortRatingButton =
                new JButton("Sort by Rating");

        sortYearButton =
                new JButton("Sort by Year");

        filterButton =
                new JButton("Rating > 8");

        topPanel.add(sortRatingButton);

        topPanel.add(sortYearButton);

        topPanel.add(filterButton);

        add(topPanel, BorderLayout.NORTH);

        // History Panel
        historyPanel = new JPanel();

        historyPanel.setLayout(
                new GridLayout(0, 3, 20, 20)
        );

        historyPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        JScrollPane scrollPane =
                new JScrollPane(historyPanel);

        add(scrollPane, BorderLayout.CENTER);

        // Load Data from Database
        loadHistoryFromDatabase();

        // Default Display
        displayMovies(historyList);

        // Sort by Rating
        sortRatingButton.addActionListener(e -> {

            historyPanel.removeAll();

            historyList.sort(
                    HistoryMovie.ratingComparator
            );

            displayMovies(historyList);

            historyPanel.revalidate();

            historyPanel.repaint();
        });

        // Sort by Year
        sortYearButton.addActionListener(e -> {

            historyPanel.removeAll();

            historyList.sort(
                    HistoryMovie.yearComparator
            );

            displayMovies(historyList);

            historyPanel.revalidate();

            historyPanel.repaint();
        });

        // Filter Rating > 8
        filterButton.addActionListener(e -> {

            historyPanel.removeAll();

            ArrayList<HistoryMovie> filteredList =
                    new ArrayList<>();

            for (HistoryMovie movie : historyList) {

                if (movie.rating > 8.0) {

                    filteredList.add(movie);
                }
            }

            displayMovies(filteredList);

            historyPanel.revalidate();

            historyPanel.repaint();
        });

        setVisible(true);
    }

    // Load History from SQLite Database
    public void loadHistoryFromDatabase() {

        try {

            Connection con =
                    DBConnection.getConnection();

            Statement stmt =
                    con.createStatement();

            ResultSet rs =
                    stmt.executeQuery(
                            "SELECT * FROM history " +
                            "WHERE username='" + loggedInUser + "'"
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

                String watchedTime =
                        rs.getString("watched_time");

                String imagePath =
                        rs.getString("image");

                HistoryMovie movie =
                        new HistoryMovie(
                                title,
                                genre,
                                rating,
                                year,
                                watchedTime,
                                imagePath
                        );

                historyList.add(movie);
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

            // toString() used here
            JLabel detailsLabel =
                    new JLabel(movie.toString());

            // Alignment
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            detailsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Add Components
            card.add(Box.createVerticalStrut(10));

            card.add(imageLabel);

            card.add(Box.createVerticalStrut(10));

            card.add(detailsLabel);

            card.add(Box.createVerticalStrut(10));

            historyPanel.add(card);
        }
    }
}