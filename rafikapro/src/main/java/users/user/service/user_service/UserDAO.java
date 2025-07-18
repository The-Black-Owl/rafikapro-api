package users.user.service.user_service;

import users.user.entity.User;

import java.util.List;

public interface UserDAO {
    User addUser(User user);
    User getUserById(Long id);
    User getUserByEmail(String email);
    User updateUser(User user);
    void deleteUserById(Long id);
    List<User> getAllUsers();
    List<User> filterByActiveUsers();
    List<User> filterByInActiveUsers();
    void deActivateUser(Long id);
    void activateUser(Long id);
}
