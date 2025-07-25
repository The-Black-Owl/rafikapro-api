package users.user.service.organizer_service;

import users.role.entity.Role;
import users.user.entity.Organizer;
import users.user.entity.User;
import users.user.reference.UserReferences;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrganizerDaoImpl implements OrganizerDAO {
    private final String connectionUrl = "jdbc:mysql://localhost:3306/rafika?user=root&password=password";

    @Override
    public Organizer createOrganizer(Organizer organizer) {
        String createOrganizerQuery="INSERT INTO organizers(user_id, " +
                "company_name, " +
                "license_number, " +
                "contact_person, " +
                "subscription_tier)" +
                "VALUES(?,?,?,?,?);";
        try (
                Connection connection= DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(createOrganizerQuery, Statement.RETURN_GENERATED_KEYS)
                ){
            preparedStatement.setLong(1,organizer.getUser().getId());
            preparedStatement.setString(2,organizer.getCompanyName());
            preparedStatement.setString(3,organizer.getLicenseNumber());
            preparedStatement.setString(4,organizer.getContactPerson());
            preparedStatement.setString(5, UserReferences.subscriptionTiers.BRONZE.toString());

            int created= preparedStatement.executeUpdate();
            if (created == 0) {
                throw new SQLException("No organizer found with user_id = " + organizer.getUser().getId());
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    organizer.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating organizer failed, no ID obtained.");
                }
            }
            return organizer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Organizer updateOrganizer(Organizer organizer) {
        String updateOrganizerQuery="UPDATE organizers SET " +
                "company_name = COALESCE(?, company_name), " +
                "license_number = COALESCE(?, license_number), " +
                "contact_person = COALESCE(?, contact_person) " +
                "WHERE user_id = ?";
        try (
                Connection connection= DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(updateOrganizerQuery)
        ){
            preparedStatement.setString(1,organizer.getCompanyName());
            preparedStatement.setString(2,organizer.getLicenseNumber());
            preparedStatement.setString(3,organizer.getContactPerson());
            preparedStatement.setLong(4,organizer.getUser().getId());

            int updated = preparedStatement.executeUpdate();
            if (updated == 0) {
                throw new SQLException("No organizer found with user_id = " + organizer.getUser().getId());
            }
            return organizer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteOrganizer(Long id) {
        String deleteQuery="DELETE FROM organizers WHERE id=?";
        try (
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(deleteQuery)
                ){
            preparedStatement.setLong(1,id);
            int deleted = preparedStatement.executeUpdate();
            if (deleted == 0) {
                throw new SQLException("No organizer found with user_id = "+id);
            }
            System.out.println("Organizer deleted removed.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Organizer> getAllOrganizers() {
        String getAllOrganizers=
                "SELECT " +
                        "o.id AS organizer_id, " +
                        "o.company_name, " +
                        "o.license_number, " +
                        "o.contact_person, " +
                        "o.subscription_tier, " +
                        "o.created_at AS organizer_created_at, " +
                        "u.id AS user_id, " +
                        "u.name AS user_name, " +
                        "u.email, " +
                        "u.phone, " +
                        "u.password, " +
                        "u.created AS user_created_at, " +
                        "u.isActive, " +
                        "r.id AS role_id, " +
                        "r.name AS role_name " +
                        "FROM organizers o " +
                        "JOIN users u ON o.user_id = u.id " +
                        "JOIN roles r ON u.role_id = r.id";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(getAllOrganizers)
                ){
            List<Organizer> organizers=new ArrayList<>();
            while(resultSet.next()){
                Role role = new Role();
                role.setId(resultSet.getLong("role_id"));
                role.setRoleName(resultSet.getString("role_name"));

                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setName(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setPassword(resultSet.getString("password"));
                user.setCreatedAt(resultSet.getTimestamp("user_created_at").toLocalDateTime());
                user.setActive(resultSet.getBoolean("isActive"));
                user.setRole(role);

                Organizer organizer = new Organizer();
                organizer.setId(resultSet.getLong("organizer_id"));
                organizer.setUser(user);
                organizer.setCompanyName(resultSet.getString("company_name"));
                organizer.setLicenseNumber(resultSet.getString("license_number"));
                organizer.setContactPerson(resultSet.getString("contact_person"));
                organizer.setSubscriptionTier(resultSet.getString("subscription_tier"));
                organizer.setCreatedAt(resultSet.getTimestamp("organizer_created_at").toLocalDateTime());

                organizers.add(organizer);
            }
            return organizers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Organizer getOrganizerById(Long id) {
        Organizer organizer = new Organizer();
        String getOrganizerById=
                "SELECT " +
                        "o.id AS organizer_id, " +
                        "o.company_name, " +
                        "o.license_number, " +
                        "o.contact_person, " +
                        "o.subscription_tier, " +
                        "o.created_at AS organizer_created_at, " +
                        "u.id AS user_id, " +
                        "u.name AS user_name, " +
                        "u.email, " +
                        "u.phone, " +
                        "u.password, " +
                        "u.created AS user_created_at, " +
                        "u.isActive, " +
                        "r.id AS role_id, " +
                        "r.name AS role_name " +
                        "FROM organizers o " +
                        "JOIN users u ON o.user_id = u.id " +
                        "JOIN roles r ON u.role_id = r.id " +
                        "WHERE o.id=?";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(getOrganizerById);
        ){
            preparedStatement.setLong(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            Optional.of(resultSet.next()).ifPresent(result->{
                try{
                    Role role = new Role();
                    role.setId(resultSet.getLong("role_id"));
                    role.setRoleName(resultSet.getString("role_name"));

                    User user = new User();
                    user.setId(resultSet.getLong("user_id"));
                    user.setName(resultSet.getString("user_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setPassword(resultSet.getString("password"));
                    user.setCreatedAt(resultSet.getTimestamp("user_created_at").toLocalDateTime());
                    user.setActive(resultSet.getBoolean("isActive"));
                    user.setRole(role);


                    organizer.setId(resultSet.getLong("organizer_id"));
                    organizer.setUser(user);
                    organizer.setCompanyName(resultSet.getString("company_name"));
                    organizer.setLicenseNumber(resultSet.getString("license_number"));
                    organizer.setContactPerson(resultSet.getString("contact_person"));
                    organizer.setSubscriptionTier(resultSet.getString("subscription_tier"));
                    organizer.setCreatedAt(resultSet.getTimestamp("organizer_created_at").toLocalDateTime());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            return organizer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Organizer getOrganizerByBusinessNumber(String licenseNumber) {
        Organizer organizer=new Organizer();
        String getOrganizerByLicenceNumber=
                "SELECT " +
                        "o.id AS organizer_id, " +
                        "o.company_name, " +
                        "o.license_number, " +
                        "o.contact_person, " +
                        "o.subscription_tier, " +
                        "o.created_at AS organizer_created_at, " +
                        "u.id AS user_id, " +
                        "u.name AS user_name, " +
                        "u.email, " +
                        "u.phone, " +
                        "u.password, " +
                        "u.created AS user_created_at, " +
                        "u.isActive, " +
                        "r.id AS role_id, " +
                        "r.name AS role_name " +
                        "FROM organizers o " +
                        "JOIN users u ON o.user_id = u.id " +
                        "JOIN roles r ON u.role_id = r.id " +
                        "WHERE UPPER(o.license_number)=UPPER(?)";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(getOrganizerByLicenceNumber)
        ){
            preparedStatement.setString(1,licenseNumber);
            ResultSet resultSet=preparedStatement.executeQuery();
            Optional.of(resultSet.next()).ifPresent(result->{
                try{
                    Role role = new Role();
                    role.setId(resultSet.getLong("role_id"));
                    role.setRoleName(resultSet.getString("role_name"));

                    User user = new User();
                    user.setId(resultSet.getLong("user_id"));
                    user.setName(resultSet.getString("user_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setPassword(resultSet.getString("password"));
                    user.setCreatedAt(resultSet.getTimestamp("user_created_at").toLocalDateTime());
                    user.setActive(resultSet.getBoolean("isActive"));
                    user.setRole(role);


                    organizer.setId(resultSet.getLong("organizer_id"));
                    organizer.setUser(user);
                    organizer.setCompanyName(resultSet.getString("company_name"));
                    organizer.setLicenseNumber(resultSet.getString("license_number"));
                    organizer.setContactPerson(resultSet.getString("contact_person"));
                    organizer.setSubscriptionTier(resultSet.getString("subscription_tier"));
                    organizer.setCreatedAt(resultSet.getTimestamp("organizer_created_at").toLocalDateTime());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            return organizer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Organizer> getOrganizerBySubscription(String subscription) {
        String getOrganizerBySubscription=
                "SELECT " +
                        "o.id AS organizer_id, " +
                        "o.company_name, " +
                        "o.license_number, " +
                        "o.contact_person, " +
                        "o.subscription_tier, " +
                        "o.created_at AS organizer_created_at, " +
                        "u.id AS user_id, " +
                        "u.name AS user_name, " +
                        "u.email, " +
                        "u.phone, " +
                        "u.password, " +
                        "u.created AS user_created_at, " +
                        "u.isActive, " +
                        "r.id AS role_id, " +
                        "r.name AS role_name " +
                        "FROM organizers o " +
                        "JOIN users u ON o.user_id = u.id " +
                        "JOIN roles r ON u.role_id = r.id " +
                        "WHERE UPPER(o.subscription_tier)=UPPER(?)";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(getOrganizerBySubscription)
        ){
            preparedStatement.setString(1,subscription);
            ResultSet resultSet=preparedStatement.executeQuery();
            List<Organizer> organizers=new ArrayList<>();
            while(resultSet.next()){
                Role role = new Role();
                role.setId(resultSet.getLong("role_id"));
                role.setRoleName(resultSet.getString("role_name"));

                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setName(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setPassword(resultSet.getString("password"));
                user.setCreatedAt(resultSet.getTimestamp("user_created_at").toLocalDateTime());
                user.setActive(resultSet.getBoolean("isActive"));
                user.setRole(role);

                Organizer organizer = new Organizer();
                organizer.setId(resultSet.getLong("organizer_id"));
                organizer.setUser(user);
                organizer.setCompanyName(resultSet.getString("company_name"));
                organizer.setLicenseNumber(resultSet.getString("license_number"));
                organizer.setContactPerson(resultSet.getString("contact_person"));
                organizer.setSubscriptionTier(resultSet.getString("subscription_tier"));
                organizer.setCreatedAt(resultSet.getTimestamp("organizer_created_at").toLocalDateTime());

                organizers.add(organizer);
            }
            return organizers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
