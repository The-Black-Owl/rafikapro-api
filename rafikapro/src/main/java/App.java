import config.DatabaseConnection;
import users.role.entity.Role;
import users.role.sevice.RoleDAOImpl;
import users.user.entity.Organizer;
import users.user.entity.User;
import users.user.service.organizer_service.OrganizerDaoImpl;
import users.user.service.user_service.UserDaoImpl;

import java.time.LocalDateTime;

public class App {
    public static void main(String[] args){
        DatabaseConnection.initialize();
        RoleDAOImpl roleDAO=new RoleDAOImpl();
        UserDaoImpl userDao=new UserDaoImpl();
        OrganizerDaoImpl organizerDAO=new OrganizerDaoImpl();

//        User user=new User();
//        Role organizerRole= roleDAO.getRoleById(2L);
//        user.setName("Alice Events");
//        user.setEmail("alice@durbanevents.co.za");
//        user.setPhone("0123456789");
//        user.setPassword("secret123");
//        user.setRole(organizerRole);
//        user.setCreatedAt(LocalDateTime.now());
//        user.setActive(true);
//
//        user = userDao.addUser(user);
//
//        System.out.println("User created with ID: " + user.getId());
//
//        Organizer organizer = new Organizer();
//        organizer.setUser(user);
//        organizer.setCompanyName("Alice's Big Events");
//        organizer.setLicenseNumber("LIC-2025-XYZ");
//        organizer.setContactPerson("Alice M.");
//        organizer.setSubscriptionTier("Steel");
//        organizer.setCreatedAt(LocalDateTime.now());
//
//        organizer = organizerDAO.createOrganizer(organizer);
//
//        System.out.println("Organizer created with ID: " + organizer.getId());

//        userDao.getAllUsers().forEach(
//                user_ -> System.out.println(user_.toString())
//        );
//
//        organizerDAO.getAllOrganizers().forEach(
//                organizer_->System.out.println(organizer_.toString())
//        );

        System.out.println(organizerDAO.getOrganizerBySubscription("bronze").toString());
    }

}
