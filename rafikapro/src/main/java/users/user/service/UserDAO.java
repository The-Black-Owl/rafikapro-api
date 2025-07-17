package users.user.service;

import users.user.entity.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);
    User getUserById(Long id);
    User getUserByEmail(String email);
    void updateUser(User user);
    void deleteUserById(Long id);
    List<User> getAllUsers();
    List<User> filterByActiveUsers();
    List<User> filterByInActiveUsers();
    void deActivateUser(Long id);
    void activateUser(Long id);
}
