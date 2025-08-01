package users.user.service.organizer_service;

import users.role.entity.Role;
import users.role.sevice.RoleDAOImpl;
import users.user.entity.Organizer;
import users.user.entity.User;
import users.user.service.user_service.UserDaoImpl;

import java.util.Scanner;

public class OrganizerService {
    private final UserDaoImpl userDao=new UserDaoImpl();
    private final OrganizerDaoImpl organizerDao=new OrganizerDaoImpl();
    private final RoleDAOImpl roleDAO=new RoleDAOImpl();
    private final User user=new User();
    private final Organizer organizer=new Organizer();

    public void registerOrganizer(Scanner scanner){
        System.out.println("\n=== Organizer Registration ===");

        //create the user
        System.out.print("Name: ");
        String name=scanner.nextLine();
        System.out.print("Email: ");
        String email=scanner.nextLine();
        System.out.print("Phone: ");
        String phone=scanner.nextLine();
        System.out.print("Password: ");
        String password=scanner.nextLine();
        Role role= roleDAO.getRoleById(2L);

        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setRole(role);

        userDao.addUser(user);
        //create organizer
        System.out.print("Company name: ");
        String companyName=scanner.nextLine();
        System.out.print("License number: ");
        String licenseNumber=scanner.nextLine();
        System.out.print("Contact person: ");
        String contactPerson=scanner.nextLine();

        organizer.setUser(user);
        organizer.setContactPerson(contactPerson);
        organizer.setCompanyName(companyName);
        organizer.setLicenseNumber(licenseNumber);

        organizerDao.createOrganizer(organizer);
        System.out.println("Organizer successfully registered.");
    }
}
