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

        //events table creation
        String createEventsTable="CREATE TABLE IF NOT EXISTS events (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "organizer_id BIGINT NOT NULL," +
                "title VARCHAR(255) NOT NULL," +
                "description TEXT," +
                "location VARCHAR(255)," +
                "start_date_time DATETIME NOT NULL," +
                "end_date_time DATETIME NOT NULL," +
                "total_tickets INT NOT NULL," +
                "tickets_sold INT DEFAULT 0," +
                "ticket_price DECIMAL(10,2) NOT NULL," +
                "category ENUM('CONFERENCE', 'SPORT', 'THEATRE', 'CONCERT', 'FESTIVAL') NOT NULL," +
                "status ENUM('PLANNED', 'ON_SALE', 'SOLD_OUT', 'CANCELLED') DEFAULT 'PLANNED'," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (organizer_id) REFERENCES organizers(id)" +
                ");";
        //tickets table creation
        String createTicketsTable =
                "CREATE TABLE IF NOT EXISTS tickets (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                        "event_id BIGINT NOT NULL," +
                        "vendor_id BIGINT," +
                        "ticket_number VARCHAR(255) UNIQUE NOT NULL," +
                        "price DECIMAL(10,2) NOT NULL," +
                        "status ENUM('AVAILABLE', 'SOLD', 'RESERVED', 'CANCELLED') DEFAULT 'AVAILABLE'," +
                        "sold_at DATETIME NULL," +
                        "is_printed BOOLEAN DEFAULT FALSE," +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "FOREIGN KEY (event_id) REFERENCES events(id)," +
                        "FOREIGN KEY (vendor_id) REFERENCES vendors(id)" +
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
            //create events query
            statement.executeUpdate(createEventsTable);
            //create tickets query
            statement.executeUpdate(createTicketsTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
