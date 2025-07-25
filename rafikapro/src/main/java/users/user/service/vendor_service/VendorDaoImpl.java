package users.user.service.vendor_service;

import users.user.entity.Vendor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
    public Vendor updateVendor(Vendor vendor) {
        String updateVendorQuery="UPDATE vendors SET() WHERE id=?";
        try(
                Connection connection= DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(updateVendorQuery)
        ){

            return vendor;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vendor> getAllVendors() {
        return null;
    }

    @Override
    public List<Vendor> getVendorByVendorType(String vendorType) {
        return null;
    }

    @Override
    public List<Vendor> getVendorBySubscriptionType(String subscription) {
        return null;
    }

    @Override
    public Vendor getVendorById(Long id) {
        return null;
    }

    @Override
    public void deleteVendorById(long id) {

    }
}
