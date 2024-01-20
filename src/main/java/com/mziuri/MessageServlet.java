package com.mziuri;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MessageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");

        try {
            Connection connection = DatabaseConnection.getConnection();
            String messages = getUserMessages(connection, username);
            response.getWriter().write(messages);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String message = request.getParameter("message");

        try {
            Connection connection = DatabaseConnection.getConnection();
            if (!userExists(connection, username) || message.contains("\n")) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid request");
            } else {
                sendMessage(connection, username, message);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Message sent successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    private String getUserMessages(Connection connection, String username) throws SQLException {
        String sql = "SELECT message FROM messages WHERE username = ?";
        StringBuilder messages = new StringBuilder();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    messages.append(resultSet.getString("message")).append("\n");
                }
            }
        }
        return messages.toString();
    }

    private void sendMessage(Connection connection, String username, String message) throws SQLException {
        String sql = "INSERT INTO MESSENGER.messages (username, message) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, message);
            statement.executeUpdate();
        }
    }

    public static boolean userExists(Connection connection, String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

}