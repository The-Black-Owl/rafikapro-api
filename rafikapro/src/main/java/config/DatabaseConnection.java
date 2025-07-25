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
        String createVendorQuery="CREATE TABLE IF NOT EXISTS vendors (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "user_id INT NOT NULL," +
                "trading_number VARCHAR(100) NOT NULL UNIQUE," +
                "vendor_type ENUM('RETAIL', 'INDEPENDENT') NOT NULL," +
                "subscription_tier VARCHAR(50) DEFAULT 'STEEL'," +
                "tickets_sold INT DEFAULT 0," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (user_id) REFERENCES users(id)" +
                ");";
        //create organizer table
        String createOrganizerTable="CREATE TABLE IF NOT EXISTS organizers (" +
                " id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                " user_id INT NOT NULL," +
                " company_name VARCHAR(100)," +
                " license_number VARCHAR(50) UNIQUE," +
                " contact_person VARCHAR(100)," +
                " created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                " subscription_tier VARCHAR(50),"+
                " FOREIGN KEY (user_id) REFERENCES users(id)" +
                ");";

        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                Statement statement=connection.createStatement()){
            // Create roles first
            statement.executeUpdate(createRoleTable);
            // Insert roles safely
            statement.executeUpdate(insertRole);
            // Create users table next
            statement.executeUpdate(createUserTable);
            //Create organizers table
            statement.executeUpdate(createOrganizerTable);
            //create vendor query
            statement.executeUpdate(createVendorQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
