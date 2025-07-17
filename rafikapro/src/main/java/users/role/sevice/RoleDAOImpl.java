package users.role.sevice;

import users.role.entity.Role;

import java.sql.*;
import java.util.Optional;

public class RoleDAOImpl implements RoleDAO{
    @Override
    public Role getRoleById(Long id) {
        String getRolebyId="SELECT * FROM roles WHERE id=?;";
        final String connectionUrl = "jdbc:mysql://localhost:3306/rafika?user=root&password=password";
        try(
                Connection connection= DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(getRolebyId)
                ){
            preparedStatement.setLong(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            Role role=new Role();
            Optional.of(resultSet.next()).ifPresent(result->{
                try {
                    role.setId(resultSet.getLong("id"));
                    role.setRoleName(resultSet.getString("name"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            return role;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
