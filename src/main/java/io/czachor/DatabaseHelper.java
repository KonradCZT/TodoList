package io.czachor;

import java.sql.*;

public class DatabaseHelper {
    private static final String Database_URL = "jdbc:sqlite:todolist.db";

    // Initialize the database connection and create the table if it doesn't exist
    public DatabaseHelper() {
        try (Connection conn = DriverManager.getConnection(Database_URL)) {
            if (conn != null) {
                initializeDatabase(conn);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Create tasks table if it doesn't exist
    private void initializeDatabase(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT NOT NULL, is_completed BOOLEAN NOT NULL);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    // Add a new task to the database
    public void addTask(String description) {
        String insertTaskSQL = "INSERT INTO tasks(description, is_completed) VALUES(?, 0)";
        try (Connection conn = DriverManager.getConnection(Database_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertTaskSQL)) {
            pstmt.setString(1, description);
            pstmt.executeUpdate();
            System.out.println("Task added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // View all tasks in the database
    public void viewTasks() {
        String selectTasksSQL = "SELECT id, description, is_completed FROM tasks";
        try (Connection conn = DriverManager.getConnection(Database_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectTasksSQL)) {

            System.out.println("\nToDo List:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                boolean isCompleted = rs.getBoolean("is_completed");

                System.out.println(id + ". " + description + " [" + (isCompleted ? "Completed" : "Not Completed") + "]");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Mark a task as completed
    public void markAsCompleted(int taskId) {
        String updateTaskSQL = "UPDATE tasks SET is_completed = 1 WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(Database_URL);
             PreparedStatement pstmt = conn.prepareStatement(updateTaskSQL)) {
            pstmt.setInt(1, taskId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task marked as completed.");
            } else {
                System.out.println("Task not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

