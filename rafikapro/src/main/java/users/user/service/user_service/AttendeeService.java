package users.user.service.user_service;

import users.role.entity.Role;
import users.role.sevice.RoleDAOImpl;
import users.user.entity.User;

import java.util.Scanner;

public class AttendeeService {
    private final UserDaoImpl userDao=new UserDaoImpl();
    private final RoleDAOImpl roleDAO=new RoleDAOImpl();
    private final User user=new User();

    public void registerAttendee(Scanner scanner){
        System.out.println("\n=== Attendee Registration ===");

        //create the user
        System.out.print("Name: ");
        String name=scanner.nextLine();
        System.out.print("Email: ");
        String email=scanner.nextLine();
        System.out.print("Phone: ");
        String phone=scanner.nextLine();
        System.out.print("Password: ");
        String password=scanner.nextLine();
        Role role= roleDAO.getRoleById(4L);

        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setRole(role);

        userDao.addUser(user);

        System.out.println("Attendee successfully registered.");
    }
}
