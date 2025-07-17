import config.DatabaseConnection;
import users.role.sevice.RoleDAOImpl;
import users.user.service.user_service.UserDaoImpl;

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
