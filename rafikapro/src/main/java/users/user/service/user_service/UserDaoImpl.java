package users.user.service.user_service;

import users.role.entity.Role;
import users.user.entity.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDAO {
    private final String connectionUrl = "jdbc:mysql://localhost:3306/rafika?user=root&password=password";

    @Override
    public User addUser(User user) {
        String createUserQuery="INSERT INTO users(name,email,phone,password,role_id,created,isActive)VALUES(?,?,?,?,?,?,?);";
        try(
                Connection connection= DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement= connection.prepareStatement(createUserQuery)){
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,user.getPhone());
            preparedStatement.setString(4,user.getPassword());
            preparedStatement.setLong(5,user.getRole().getId());
            preparedStatement.setTimestamp(6,Timestamp.valueOf(user.getCreatedAt()));
            preparedStatement.setBoolean(7,user.isActive());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1)); // set the new ID
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            System.out.println("User created with ID: " + user.getId());
            return user;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserById(Long id) {
        User user=new User();
        String getUserByIdQuery="SELECT u.*,r.id AS role_id, r.name AS role_name FROM users u JOIN roles r ON r.id=u.role_id WHERE u.id=?";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(getUserByIdQuery);
                ){
            preparedStatement.setLong(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            Optional.of(resultSet.next()).ifPresent(result->{
                try{
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setCreatedAt(LocalDateTime.parse(
                            resultSet.getString("created"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    user.setActive(resultSet.getBoolean("isActive"));
                    Role role = new Role();
                    role.setId(resultSet.getLong("role_id"));
                    role.setRoleName(resultSet.getString("role_name"));
                    user.setRole(role);
                } catch (Exception e) {
                    throw new RuntimeException(e);//change to a user not found expression
                }
            });
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        User user=new User();
        String getUserByIdQuery="SELECT u.*,r.id AS role_id, r.name AS role_name FROM users u JOIN roles r ON r.id=u.role_id WHERE u.email=?";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(getUserByIdQuery);
        ){
            preparedStatement.setString(1,email);
            ResultSet resultSet= preparedStatement.executeQuery();
            Optional.of(resultSet.next()).ifPresent(result->{
                try{
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setCreatedAt(LocalDateTime.parse(
                            resultSet.getString("created"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    user.setActive(resultSet.getBoolean("isActive"));
                    Role role = new Role();
                    role.setId(resultSet.getLong("role_id"));
                    role.setRoleName(resultSet.getString("role_name"));
                    user.setRole(role);
                } catch (Exception e) {
                    throw new RuntimeException(e);//change to a user not found expression
                }
            });
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User updateUser(User user) {
        String updateQuery="UPDATE users SET name=?,phone=? WHERE id=?";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(updateQuery);
                ){
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhone());
            preparedStatement.setLong(3,user.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            System.out.println("User updated with ID: " + user.getId());
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        String deleteQuery="DELETE FROM users WHERE id=?";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(deleteQuery);
                ){
            preparedStatement.setLong(1,id);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }

            System.out.println("User has been deleted " );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users=new ArrayList<>();
        String query = "SELECT u.*, r.id AS role_id, r.name AS role_name " +
                "FROM users u JOIN roles r ON u.role_id = r.id;";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery(query);
        ){
            while(resultSet.next()){
                User user=new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setCreatedAt(LocalDateTime.parse(
                        resultSet.getString("created"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                user.setActive(resultSet.getBoolean("isActive"));
                Role role = new Role();
                role.setId(resultSet.getLong("role_id"));
                role.setRoleName(resultSet.getString("role_name"));
                user.setRole(role);
                users.add(user);            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public List<User> filterByActiveUsers() {
        List<User> users=new ArrayList<>();
        String query = "SELECT u.*, r.id AS role_id, r.name AS role_name " +
                "FROM users u JOIN roles r ON u.role_id = r.id WHERE u.isActive=TRUE;";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery(query);
        ){
            while(resultSet.next()){
                User user=new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setCreatedAt(LocalDateTime.parse(
                        resultSet.getString("created"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                user.setActive(resultSet.getBoolean("isActive"));
                Role role = new Role();
                role.setId(resultSet.getLong("role_id"));
                role.setRoleName(resultSet.getString("role_name"));
                user.setRole(role);
                users.add(user);            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public List<User> filterByInActiveUsers() {
        List<User> users=new ArrayList<>();
        String query = "SELECT u.*, r.id AS role_id, r.name AS role_name " +
                "FROM users u JOIN roles r ON u.role_id = r.id WHERE u.isActive=FALSE;";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery(query);
        ){
            while(resultSet.next()){
                User user=new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setCreatedAt(LocalDateTime.parse(
                        resultSet.getString("created"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                user.setActive(resultSet.getBoolean("isActive"));
                Role role = new Role();
                role.setId(resultSet.getLong("role_id"));
                role.setRoleName(resultSet.getString("role_name"));
                user.setRole(role);
                users.add(user);            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void deActivateUser(Long id) {
        String updateUserActiveStatus="UPDATE users SET isActive=false WHERE id=?";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(updateUserActiveStatus)
                ){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void activateUser(Long id) {
        String updateUserActiveStatus="UPDATE users SET isActive=true WHERE id=?";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(updateUserActiveStatus)
        ){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
