import config.DatabaseConnection;
import users.role.entity.Role;
import users.role.sevice.RoleDAOImpl;
import users.user.entity.User;
import users.user.service.UserDaoImpl;

import java.time.LocalDateTime;

public class App {
    public static void main(String[] args){
        DatabaseConnection.initialize();
        RoleDAOImpl roleDAO=new RoleDAOImpl();
        UserDaoImpl userDao=new UserDaoImpl();
        userDao.getAllUsers().forEach(
                user -> System.out.println(user.toString())
        );

    }
}
