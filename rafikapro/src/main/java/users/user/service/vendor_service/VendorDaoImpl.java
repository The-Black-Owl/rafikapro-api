package users.user.service.vendor_service;

import users.role.entity.Role;
import users.user.entity.User;
import users.user.entity.Vendor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VendorDaoImpl implements VendorDao {
    private final String connectionUrl = "jdbc:mysql://localhost:3306/rafika?user=root&password=password";

    @Override
    public Vendor createVendor(Vendor vendor) {
        String createVendorQuery="INSERT INTO vendors(user_id," +
                "trading_number," +
                "vendor_type) VALUES(?,?,?);";
        try(
                Connection connection= DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(createVendorQuery)
                ){
            preparedStatement.setInt(1,(int)vendor.getUser().getId());
            preparedStatement.setString(2,vendor.getTradingNumber());
            preparedStatement.setString(3,vendor.getVendorType());

            int created=preparedStatement.executeUpdate();
            if(created==0){
                throw new SQLException("Vendor not created");
            }
            return vendor;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Vendor updateVendor(Long id,String tradingNumber) {
        String updateVendorQuery="UPDATE vendors SET(" +
                "trading_number=?"+
        ") WHERE id=?";
        try(
                Connection connection= DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(updateVendorQuery)
        ){
            preparedStatement.setString(1,tradingNumber);
            preparedStatement.setLong(2,id);
            int updated= preparedStatement.executeUpdate();
            if(updated==0){
                throw new SQLException("Vendor with id = "+id+" is not found");
            }
            return getVendorById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vendor> getAllVendors() {
        List<Vendor> vendors=new ArrayList<>();
        String getAllVendors="SELECT " +
                "v.id AS organizer_id, " +
                "v.trading_number, " +
                "v.vendor_type, " +
                "v.tickets_sold, " +
                "v.subscription_tier, " +
                "v.created_at AS vendor_created_at, " +
                "u.id AS user_id, " +
                "u.name AS user_name, " +
                "u.email, " +
                "u.phone, " +
                "u.password, " +
                "u.created AS user_created_at, " +
                "u.isActive, " +
                "r.id AS role_id, " +
                "r.name AS role_name " +
                "FROM vendors v " +
                "JOIN users u ON v.user_id = u.id " +
                "JOIN roles r ON u.role_id = r.id ";
        try (
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(getAllVendors);
                ResultSet resultSet= preparedStatement.executeQuery()

        ){
            while(resultSet.next()){
                try{
                    Role role = new Role();
                    role.setId(resultSet.getLong("role_id"));
                    role.setRoleName(resultSet.getString("role_name"));

                    User user = new User();
                    user.setId(resultSet.getLong("user_id"));
                    user.setName(resultSet.getString("user_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setPassword(resultSet.getString("password"));
                    user.setCreatedAt(resultSet.getTimestamp("user_created_at").toLocalDateTime());
                    user.setActive(resultSet.getBoolean("isActive"));
                    user.setRole(role);

                    Vendor vendor=new Vendor();
                    vendor.setTradingNumber(resultSet.getString("trading_number"));
                    vendor.setUser(user);
                    vendor.setVendorType(resultSet.getString("vendor_type"));
                    vendor.setSubscriptionTier(resultSet.getString("subscription_tier"));
                    vendor.setTicketsSold(resultSet.getInt("tickets_sold"));
                    vendor.setCreatedAt(resultSet.getTimestamp("vendor_created_at").toLocalDateTime());

                    vendors.add(vendor);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vendors;
    }

    @Override
    public List<Vendor> getVendorByVendorType(String vendorType) {
        List<Vendor> vendors=new ArrayList<>();
        String getVendorsByVendorType="SELECT " +
                "v.id AS organizer_id, " +
                "v.trading_number, " +
                "v.vendor_type, " +
                "v.tickets_sold, " +
                "v.subscription_tier, " +
                "v.created_at AS vendor_created_at, " +
                "u.id AS user_id, " +
                "u.name AS user_name, " +
                "u.email, " +
                "u.phone, " +
                "u.password, " +
                "u.created AS user_created_at, " +
                "u.isActive, " +
                "r.id AS role_id, " +
                "r.name AS role_name " +
                "FROM vendors v " +
                "JOIN users u ON v.user_id = u.id " +
                "JOIN roles r ON u.role_id = r.id " +
                "WHERE v.vendor_type=?";
        try (
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(getVendorsByVendorType)

        ){
            preparedStatement.setString(1,vendorType.toUpperCase());
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                try{
                    Role role = new Role();
                    role.setId(resultSet.getLong("role_id"));
                    role.setRoleName(resultSet.getString("role_name"));

                    User user = new User();
                    user.setId(resultSet.getLong("user_id"));
                    user.setName(resultSet.getString("user_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setPassword(resultSet.getString("password"));
                    user.setCreatedAt(resultSet.getTimestamp("user_created_at").toLocalDateTime());
                    user.setActive(resultSet.getBoolean("isActive"));
                    user.setRole(role);

                    Vendor vendor=new Vendor();
                    vendor.setTradingNumber(resultSet.getString("trading_number"));
                    vendor.setUser(user);
                    vendor.setVendorType(resultSet.getString("vendor_type"));
                    vendor.setSubscriptionTier(resultSet.getString("subscription_tier"));
                    vendor.setTicketsSold(resultSet.getInt("tickets_sold"));
                    vendor.setCreatedAt(resultSet.getTimestamp("vendor_created_at").toLocalDateTime());

                    vendors.add(vendor);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vendors;
    }

    @Override
    public List<Vendor> getVendorBySubscriptionType(String subscription) {
        List<Vendor> vendors=new ArrayList<>();
        String getAllVendors="SELECT " +
                "v.id AS organizer_id, " +
                "v.trading_number, " +
                "v.vendor_type, " +
                "v.tickets_sold, " +
                "v.subscription_tier, " +
                "v.created_at AS vendor_created_at, " +
                "u.id AS user_id, " +
                "u.name AS user_name, " +
                "u.email, " +
                "u.phone, " +
                "u.password, " +
                "u.created AS user_created_at, " +
                "u.isActive, " +
                "r.id AS role_id, " +
                "r.name AS role_name " +
                "FROM vendors v " +
                "JOIN users u ON v.user_id = u.id " +
                "JOIN roles r ON u.role_id = r.id " +
                "WHERE v.subscription_tier=?";
        try (
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(getAllVendors)

        ){
            preparedStatement.setString(1,subscription.toUpperCase());
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                try{
                    Role role = new Role();
                    role.setId(resultSet.getLong("role_id"));
                    role.setRoleName(resultSet.getString("role_name"));

                    User user = new User();
                    user.setId(resultSet.getLong("user_id"));
                    user.setName(resultSet.getString("user_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setPassword(resultSet.getString("password"));
                    user.setCreatedAt(resultSet.getTimestamp("user_created_at").toLocalDateTime());
                    user.setActive(resultSet.getBoolean("isActive"));
                    user.setRole(role);

                    Vendor vendor=new Vendor();
                    vendor.setTradingNumber(resultSet.getString("trading_number"));
                    vendor.setUser(user);
                    vendor.setVendorType(resultSet.getString("vendor_type"));
                    vendor.setSubscriptionTier(resultSet.getString("subscription_tier"));
                    vendor.setTicketsSold(resultSet.getInt("tickets_sold"));
                    vendor.setCreatedAt(resultSet.getTimestamp("vendor_created_at").toLocalDateTime());

                    vendors.add(vendor);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vendors;
    }

    @Override
    public Vendor getVendorById(Long id) {
        Vendor vendor=new Vendor();
        String getVendorByID="SELECT " +
                "v.id AS organizer_id, " +
                "v.trading_number, " +
                "v.vendor_type, " +
                "v.tickets_sold, " +
                "v.subscription_tier, " +
                "v.created_at AS vendor_created_at, " +
                "u.id AS user_id, " +
                "u.name AS user_name, " +
                "u.email, " +
                "u.phone, " +
                "u.password, " +
                "u.created AS user_created_at, " +
                "u.isActive, " +
                "r.id AS role_id, " +
                "r.name AS role_name " +
                "FROM vendors v " +
                "JOIN users u ON v.user_id = u.id " +
                "JOIN roles r ON u.role_id = r.id " +
                "WHERE v.id=?";
        try (
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(getVendorByID)
                ){
            preparedStatement.setLong(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            Optional.of(resultSet.next()).ifPresent(result->{
                try{
                    Role role = new Role();
                    role.setId(resultSet.getLong("role_id"));
                    role.setRoleName(resultSet.getString("role_name"));

                    User user = new User();
                    user.setId(resultSet.getLong("user_id"));
                    user.setName(resultSet.getString("user_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setPassword(resultSet.getString("password"));
                    user.setCreatedAt(resultSet.getTimestamp("user_created_at").toLocalDateTime());
                    user.setActive(resultSet.getBoolean("isActive"));
                    user.setRole(role);

                    vendor.setTradingNumber(resultSet.getString("trading_number"));
                    vendor.setUser(user);
                    vendor.setVendorType(resultSet.getString("vendor_type"));
                    vendor.setSubscriptionTier(resultSet.getString("subscription_tier"));
                    vendor.setTicketsSold(resultSet.getInt("tickets_sold"));
                    vendor.setCreatedAt(resultSet.getTimestamp("vendor_created_at").toLocalDateTime());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vendor;
    }

    @Override
    public void deleteVendorById(long id) {
        String deleteVendorQuery="DELETE FROM vendors WHERE id=?";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(deleteVendorQuery)
                ){
            preparedStatement.setLong(1,id);
            int deleted=preparedStatement.executeUpdate();
            if(deleted==0){
                throw new SQLException("Vendor is not found");
            }
            System.out.println("Vendor has been deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
