package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.DBConnection;

public class LoginFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    JButton loginButton;
    JButton registerButton;

    public LoginFrame() {

        // Window Title
        setTitle("Movie Streaming System");

        // Window Size
        setSize(450, 250);

        // Close Application Properly
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center Window
        setLocationRelativeTo(null);

        // Prevent Resizing
        setResizable(false);

        // Main Panel
        JPanel panel = new JPanel();

        // Layout
        panel.setLayout(new GridLayout(3, 2, 15, 15));

        // Padding
        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        // Labels
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        // Fields
        usernameField = new JTextField();

        passwordField = new JPasswordField();

        // Buttons
        loginButton = new JButton("Login");

        registerButton = new JButton("Register");

        // Add Components
        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(loginButton);
        panel.add(registerButton);

        // Add Panel to Frame
        add(panel);

        // Register Button Logic
        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();

                String password =
                        String.valueOf(passwordField.getPassword());

                try {

                    Connection con =
                            DBConnection.getConnection();

                    // Check existing username
                    String checkQuery =
                            "SELECT * FROM users WHERE username=?";

                    PreparedStatement checkPs =
                            con.prepareStatement(checkQuery);

                    checkPs.setString(1, username);

                    ResultSet rs =
                            checkPs.executeQuery();

                    if (rs.next()) {

                        JOptionPane.showMessageDialog(
                                null,
                                "Username already exists"
                        );

                    } else {

                        // Insert new user
                        String query =
                                "INSERT INTO users(username, password) VALUES(?, ?)";

                        PreparedStatement ps =
                                con.prepareStatement(query);

                        ps.setString(1, username);

                        ps.setString(2, password);

                        ps.executeUpdate();

                        JOptionPane.showMessageDialog(
                                null,
                                "Registration Successful"
                        );
                    }

                    con.close();

                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            }
        });

        // Login Button Logic
        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();

                String password =
                        String.valueOf(passwordField.getPassword());

                try {

                    Connection con =
                            DBConnection.getConnection();

                    String query =
                            "SELECT * FROM users WHERE username=? AND password=?";

                    PreparedStatement ps =
                            con.prepareStatement(query);

                    ps.setString(1, username);

                    ps.setString(2, password);

                    ResultSet rs =
                            ps.executeQuery();

                    if (rs.next()) {

                        JOptionPane.showMessageDialog(
                                null,
                                "Login Successful"
                        );

                        dispose();

                        new Dashboard(username);

                    } else {

                        JOptionPane.showMessageDialog(
                                null,
                                "Invalid Username or Password"
                        );
                    }

                    con.close();

                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            }
        });

        // Show Window
        setVisible(true);
    }

    public static void main(String[] args) {

        new LoginFrame();
    }
}