package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    public static void initialize(){
        String connectionUrl = "jdbc:mysql://localhost:3306/rafika?user=root&password=password&createDatabaseIfNotExist=true";

        //Create Role table
        String createRoleTable="CREATE TABLE IF NOT EXISTS roles (" +
                "    id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "    name VARCHAR(50) NOT NULL UNIQUE" +
                ");";
        //Insert roles
        String insertRole="INSERT IGNORE INTO roles (name) VALUES" +
                "  ('ADMIN')," +
                "  ('ORGANIZER')," +
                "  ('VENDOR')," +
                "  ('ATTENDEE');";
        //create User table
        String createUserTable="CREATE TABLE IF NOT EXISTS users(" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(50) NOT NULL," +
                "email VARCHAR(255) NOT NULL UNIQUE," +
                "phone VARCHAR(50)," +
                "password VARCHAR(50) NOT NULL," +
                "role_id BIGINT NOT NULL," +
                "created TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "isActive BOOLEAN," +
                "FOREIGN KEY (role_id) REFERENCES roles(id));";
        //create vendor table
        //create organizer table

        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                Statement statement=connection.createStatement()){
            // Create roles first
            statement.executeUpdate(createRoleTable);

            // Insert roles safely
            statement.executeUpdate(insertRole);

            // Create users table next
            statement.executeUpdate(createUserTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
