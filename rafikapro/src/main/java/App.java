import config.DatabaseConnection;
import users.role.entity.Role;
import users.role.sevice.RoleDAOImpl;
import users.user.entity.Organizer;
import users.user.entity.User;
import users.user.entity.Vendor;
import users.user.reference.UserReferences;
import users.user.service.organizer_service.OrganizerDaoImpl;
import users.user.service.user_service.UserDaoImpl;
import users.user.service.vendor_service.VendorDaoImpl;

import java.time.LocalDateTime;

public class App {
    public static void main(String[] args){
        DatabaseConnection.initialize();
        RoleDAOImpl roleDAO=new RoleDAOImpl();
        UserDaoImpl userDao=new UserDaoImpl();
        OrganizerDaoImpl organizerDAO=new OrganizerDaoImpl();
        VendorDaoImpl vendorDao=new VendorDaoImpl();

        User user= userDao.getUserById(5L);

        Vendor vendor=new Vendor();
        vendor.setUser(user);
        vendor.setVendorType(UserReferences.vendorType.INDEPENDENT.toString());
        vendor.setTradingNumber("AAAA12356");
        Vendor newVendor= vendorDao.createVendor(vendor);

        System.out.println(newVendor.toString());
    }

}
