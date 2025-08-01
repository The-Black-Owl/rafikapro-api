package users.user.service.vendor_service;

import users.role.entity.Role;
import users.role.sevice.RoleDAOImpl;
import users.user.entity.User;
import users.user.entity.Vendor;
import users.user.reference.UserReferences;
import users.user.service.user_service.UserDaoImpl;

import java.util.Scanner;

public class VendorService {
    private final UserDaoImpl userDao=new UserDaoImpl();
    private final VendorDaoImpl vendorDao=new VendorDaoImpl();
    private final RoleDAOImpl roleDAO=new RoleDAOImpl();
    private final User user=new User();
    private final Vendor vendor=new Vendor();

    public void registerVendor(Scanner scanner){
        System.out.println("\n=== Vendor Registration ===");

        System.out.print("Name: ");
        String name=scanner.nextLine();
        System.out.print("Email: ");
        String email=scanner.nextLine();
        System.out.print("Phone: ");
        String phone=scanner.nextLine();
        System.out.print("Password: ");
        String password=scanner.nextLine();
        Role role= roleDAO.getRoleById(3L);

        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setRole(role);

        System.out.print("Trading number: ");
        String tradingNumber=scanner.nextLine();
        System.out.print("Vendor Type: \n[1] Retail\n[2] Independent\nChoose type[1-2]:");
        int choice= scanner.nextInt();
        switch(choice){
            case 1:
                userDao.addUser(user);
                vendor.setUser(user);
                vendor.setVendorType(UserReferences.vendorType.RETAIL.toString());
                vendor.setTradingNumber(tradingNumber);
                vendorDao.createVendor(vendor);
                break;
            case 2:
                userDao.addUser(user);
                vendor.setUser(user);
                vendor.setVendorType(UserReferences.vendorType.INDEPENDENT.toString());
                vendor.setTradingNumber(tradingNumber);
                vendorDao.createVendor(vendor);
                break;
            default:
                System.out.println("No valid choice was made");
                break;
        }

        System.out.println("Vendor successfully registered.");
    }
}
