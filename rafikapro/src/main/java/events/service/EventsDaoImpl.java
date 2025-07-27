package events.service;

import events.entity.Event;
import users.role.entity.Role;
import users.user.entity.Organizer;
import users.user.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventsDaoImpl implements EventsDao{
    private final String connectionUrl = "jdbc:mysql://localhost:3306/rafika?user=root&password=password";

    @Override
    public Event createEvent(Event event) {
        String createEventQuery="INSERT INTO events" +
                "(organizer_id," +
                "title," +
                "description," +
                "location," +
                "start_date_time," +
                "end_date_time," +
                "total_tickets," +
                "ticket_price," +
                "category)" +
                "VALUES(?,?,?,?,?,?,?,?,?);";
        try(
                Connection connection= DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(createEventQuery)
                ){

            preparedStatement.setLong(1,event.getOrganizer().getId());
            preparedStatement.setString(2,event.getTitle());
            preparedStatement.setString(3,event.getDescription());
            preparedStatement.setString(4,event.getLocation());
            preparedStatement.setTimestamp(5,Timestamp.valueOf(event.getStartDateTime()));
            preparedStatement.setTimestamp(6,Timestamp.valueOf(event.getEndDateTime()));
            preparedStatement.setInt(7,event.getTotalTickets());
            preparedStatement.setDouble(8,event.getTicketPrice());
            preparedStatement.setString(9,event.getCategory());

            int created= preparedStatement.executeUpdate();
            if(created==0){
                throw new SQLException("Resource not created");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return event;
    }

    @Override
    public Event getEventById(Long id) {
        Event event = new Event();
        String getEventById=
                "SELECT " +
                        "  e.id AS event_id, " +
                        "  e.title, " +
                        "  e.description, " +
                        "  e.location, " +
                        "  e.start_date_time, " +
                        "  e.end_date_time, " +
                        "  e.total_tickets, " +
                        "  e.tickets_sold, " +
                        "  e.ticket_price, " +
                        "  e.category, " +
                        "  e.status, " +
                        "  e.created_at AS event_created_at, " +
                        "  o.id AS organizer_id, " +
                        "  o.company_name, " +
                        "  o.license_number, " +
                        "  o.contact_person, " +
                        "  o.subscription_tier, " +
                        "  o.created_at AS organizer_created_at, " +
                        "  u.id AS user_id, " +
                        "  u.name AS user_name, " +
                        "  u.email, " +
                        "  u.phone, " +
                        "  u.password, " +
                        "  u.created AS user_created_at, " +
                        "  u.isActive, " +
                        "  r.id AS roles_id, " +
                        "  r.name AS role_name " +
                        "FROM events e " +
                        "JOIN organizers o ON e.organizer_id = o.id " +
                        "JOIN users u ON o.user_id = u.id " +
                        "JOIN roles r ON u.role_id = r.id " +
                        "WHERE e.id = ?";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(getEventById)
                ){
            preparedStatement.setLong(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            Optional.of(resultSet.next()).ifPresent(result->{
                try{
                    Role role = new Role();
                    role.setId(resultSet.getLong("roles_id"));
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
                    organizer.setCompanyName(resultSet.getString("company_name"));
                    organizer.setLicenseNumber(resultSet.getString("license_number"));
                    organizer.setContactPerson(resultSet.getString("contact_person"));
                    organizer.setSubscriptionTier(resultSet.getString("subscription_tier"));
                    organizer.setCreatedAt(resultSet.getTimestamp("organizer_created_at").toLocalDateTime());
                    organizer.setUser(user);

                    event.setId(resultSet.getLong("event_id"));
                    event.setTitle(resultSet.getString("title"));
                    event.setDescription(resultSet.getString("description"));
                    event.setLocation(resultSet.getString("location"));
                    event.setStartDateTime(resultSet.getTimestamp("start_date_time").toLocalDateTime());
                    event.setEndDateTime(resultSet.getTimestamp("end_date_time").toLocalDateTime());
                    event.setTotalTickets(resultSet.getInt("total_tickets"));
                    event.setTicketsSold(resultSet.getInt("tickets_sold"));
                    event.setTicketPrice(resultSet.getDouble("ticket_price"));
                    event.setCategory(resultSet.getString("category"));
                    event.setStatus(resultSet.getString("status"));
                    event.setCreated_at(resultSet.getTimestamp("event_created_at").toLocalDateTime());
                    event.setOrganizer(organizer);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return event;
    }

    @Override
    public List<Event> getAllEvents() {
        List<Event> events=new ArrayList<>();
        String getAllEventBy=
                "SELECT " +
                        "  e.id AS event_id, " +
                        "  e.title, " +
                        "  e.description, " +
                        "  e.location, " +
                        "  e.start_date_time, " +
                        "  e.end_date_time, " +
                        "  e.total_tickets, " +
                        "  e.tickets_sold, " +
                        "  e.ticket_price, " +
                        "  e.category, " +
                        "  e.status, " +
                        "  e.created_at AS event_created_at, " +
                        "  o.id AS organizer_id, " +
                        "  o.company_name, " +
                        "  o.license_number, " +
                        "  o.contact_person, " +
                        "  o.subscription_tier, " +
                        "  o.created_at AS organizer_created_at, " +
                        "  u.id AS user_id, " +
                        "  u.name AS user_name, " +
                        "  u.email, " +
                        "  u.phone, " +
                        "  u.password, " +
                        "  u.created AS user_created_at, " +
                        "  u.isActive, " +
                        "  r.id AS roles_id, " +
                        "  r.name AS role_name " +
                        "FROM events e " +
                        "JOIN organizers o ON e.organizer_id = o.id " +
                        "JOIN users u ON o.user_id = u.id " +
                        "JOIN roles r ON u.role_id = r.id ";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                Statement statement=connection.createStatement();
                ResultSet resultSet= statement.executeQuery(getAllEventBy)
        ){
            while(resultSet.next()){
                try{
                    Role role = new Role();
                    role.setId(resultSet.getLong("roles_id"));
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
                    organizer.setCompanyName(resultSet.getString("company_name"));
                    organizer.setLicenseNumber(resultSet.getString("license_number"));
                    organizer.setContactPerson(resultSet.getString("contact_person"));
                    organizer.setSubscriptionTier(resultSet.getString("subscription_tier"));
                    organizer.setCreatedAt(resultSet.getTimestamp("organizer_created_at").toLocalDateTime());
                    organizer.setUser(user);

                    Event event = new Event();
                    event.setId(resultSet.getLong("event_id"));
                    event.setTitle(resultSet.getString("title"));
                    event.setDescription(resultSet.getString("description"));
                    event.setLocation(resultSet.getString("location"));
                    event.setStartDateTime(resultSet.getTimestamp("start_date_time").toLocalDateTime());
                    event.setEndDateTime(resultSet.getTimestamp("end_date_time").toLocalDateTime());
                    event.setTotalTickets(resultSet.getInt("total_tickets"));
                    event.setTicketsSold(resultSet.getInt("tickets_sold"));
                    event.setTicketPrice(resultSet.getDouble("ticket_price"));
                    event.setCategory(resultSet.getString("category"));
                    event.setStatus(resultSet.getString("status"));
                    event.setCreated_at(resultSet.getTimestamp("event_created_at").toLocalDateTime());
                    event.setOrganizer(organizer);
                    events.add(event);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    @Override
    public List<Event> getEventsByOrganizer(Long organizerId) {
        List<Event> events=new ArrayList<>();
        String getAllEventBy=
                "SELECT " +
                        "  e.id AS event_id, " +
                        "  e.title, " +
                        "  e.description, " +
                        "  e.location, " +
                        "  e.start_date_time, " +
                        "  e.end_date_time, " +
                        "  e.total_tickets, " +
                        "  e.tickets_sold, " +
                        "  e.ticket_price, " +
                        "  e.category, " +
                        "  e.status, " +
                        "  e.created_at AS event_created_at, " +
                        "  o.id AS organizer_id, " +
                        "  o.company_name, " +
                        "  o.license_number, " +
                        "  o.contact_person, " +
                        "  o.subscription_tier, " +
                        "  o.created_at AS organizer_created_at, " +
                        "  u.id AS user_id, " +
                        "  u.name AS user_name, " +
                        "  u.email, " +
                        "  u.phone, " +
                        "  u.password, " +
                        "  u.created AS user_created_at, " +
                        "  u.isActive, " +
                        "  r.id AS roles_id, " +
                        "  r.name AS role_name " +
                        "FROM events e " +
                        "JOIN organizers o ON e.organizer_id = o.id " +
                        "JOIN users u ON o.user_id = u.id " +
                        "JOIN roles r ON u.role_id = r.id " +
                        "WHERE o.id = ?";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(getAllEventBy)
        ){
            preparedStatement.setLong(1,organizerId);
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                try{
                    Role role = new Role();
                    role.setId(resultSet.getLong("roles_id"));
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
                    organizer.setCompanyName(resultSet.getString("company_name"));
                    organizer.setLicenseNumber(resultSet.getString("license_number"));
                    organizer.setContactPerson(resultSet.getString("contact_person"));
                    organizer.setSubscriptionTier(resultSet.getString("subscription_tier"));
                    organizer.setCreatedAt(resultSet.getTimestamp("organizer_created_at").toLocalDateTime());
                    organizer.setUser(user);

                    Event event = new Event();
                    event.setId(resultSet.getLong("event_id"));
                    event.setTitle(resultSet.getString("title"));
                    event.setDescription(resultSet.getString("description"));
                    event.setLocation(resultSet.getString("location"));
                    event.setStartDateTime(resultSet.getTimestamp("start_date_time").toLocalDateTime());
                    event.setEndDateTime(resultSet.getTimestamp("end_date_time").toLocalDateTime());
                    event.setTotalTickets(resultSet.getInt("total_tickets"));
                    event.setTicketsSold(resultSet.getInt("tickets_sold"));
                    event.setTicketPrice(resultSet.getDouble("ticket_price"));
                    event.setCategory(resultSet.getString("category"));
                    event.setStatus(resultSet.getString("status"));
                    event.setCreated_at(resultSet.getTimestamp("event_created_at").toLocalDateTime());
                    event.setOrganizer(organizer);
                    events.add(event);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    @Override
    public List<Event> getEventsByCategory(String category) {
        List<Event> events=new ArrayList<>();
        String getAllEventBy=
                "SELECT " +
                        "  e.id AS event_id, " +
                        "  e.title, " +
                        "  e.description, " +
                        "  e.location, " +
                        "  e.start_date_time, " +
                        "  e.end_date_time, " +
                        "  e.total_tickets, " +
                        "  e.tickets_sold, " +
                        "  e.ticket_price, " +
                        "  e.category, " +
                        "  e.status, " +
                        "  e.created_at AS event_created_at, " +
                        "  o.id AS organizer_id, " +
                        "  o.company_name, " +
                        "  o.license_number, " +
                        "  o.contact_person, " +
                        "  o.subscription_tier, " +
                        "  o.created_at AS organizer_created_at, " +
                        "  u.id AS user_id, " +
                        "  u.name AS user_name, " +
                        "  u.email, " +
                        "  u.phone, " +
                        "  u.password, " +
                        "  u.created AS user_created_at, " +
                        "  u.isActive, " +
                        "  r.id AS roles_id, " +
                        "  r.name AS role_name " +
                        "FROM events e " +
                        "JOIN organizers o ON e.organizer_id = o.id " +
                        "JOIN users u ON o.user_id = u.id " +
                        "JOIN roles r ON u.role_id = r.id " +
                        "WHERE e.category = ?";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(getAllEventBy)
        ){
            preparedStatement.setString(1,category);
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                try{
                    Role role = new Role();
                    role.setId(resultSet.getLong("roles_id"));
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
                    organizer.setCompanyName(resultSet.getString("company_name"));
                    organizer.setLicenseNumber(resultSet.getString("license_number"));
                    organizer.setContactPerson(resultSet.getString("contact_person"));
                    organizer.setSubscriptionTier(resultSet.getString("subscription_tier"));
                    organizer.setCreatedAt(resultSet.getTimestamp("organizer_created_at").toLocalDateTime());
                    organizer.setUser(user);

                    Event event = new Event();
                    event.setId(resultSet.getLong("event_id"));
                    event.setTitle(resultSet.getString("title"));
                    event.setDescription(resultSet.getString("description"));
                    event.setLocation(resultSet.getString("location"));
                    event.setStartDateTime(resultSet.getTimestamp("start_date_time").toLocalDateTime());
                    event.setEndDateTime(resultSet.getTimestamp("end_date_time").toLocalDateTime());
                    event.setTotalTickets(resultSet.getInt("total_tickets"));
                    event.setTicketsSold(resultSet.getInt("tickets_sold"));
                    event.setTicketPrice(resultSet.getDouble("ticket_price"));
                    event.setCategory(resultSet.getString("category"));
                    event.setStatus(resultSet.getString("status"));
                    event.setCreated_at(resultSet.getTimestamp("event_created_at").toLocalDateTime());
                    event.setOrganizer(organizer);
                    events.add(event);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    @Override
    public List<Event> getEventsByStatus(String status) {
        List<Event> events=new ArrayList<>();
        String getAllEventBy=
                "SELECT " +
                        "  e.id AS event_id, " +
                        "  e.title, " +
                        "  e.description, " +
                        "  e.location, " +
                        "  e.start_date_time, " +
                        "  e.end_date_time, " +
                        "  e.total_tickets, " +
                        "  e.tickets_sold, " +
                        "  e.ticket_price, " +
                        "  e.category, " +
                        "  e.status, " +
                        "  e.created_at AS event_created_at, " +
                        "  o.id AS organizer_id, " +
                        "  o.company_name, " +
                        "  o.license_number, " +
                        "  o.contact_person, " +
                        "  o.subscription_tier, " +
                        "  o.created_at AS organizer_created_at, " +
                        "  u.id AS user_id, " +
                        "  u.name AS user_name, " +
                        "  u.email, " +
                        "  u.phone, " +
                        "  u.password, " +
                        "  u.created AS user_created_at, " +
                        "  u.isActive, " +
                        "  r.id AS roles_id, " +
                        "  r.name AS role_name " +
                        "FROM events e " +
                        "JOIN organizers o ON e.organizer_id = o.id " +
                        "JOIN users u ON o.user_id = u.id " +
                        "JOIN roles r ON u.role_id = r.id " +
                        "WHERE e.status = ?";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(getAllEventBy)
        ){
            preparedStatement.setString(1,status);
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                try{
                    Role role = new Role();
                    role.setId(resultSet.getLong("roles_id"));
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
                    organizer.setCompanyName(resultSet.getString("company_name"));
                    organizer.setLicenseNumber(resultSet.getString("license_number"));
                    organizer.setContactPerson(resultSet.getString("contact_person"));
                    organizer.setSubscriptionTier(resultSet.getString("subscription_tier"));
                    organizer.setCreatedAt(resultSet.getTimestamp("organizer_created_at").toLocalDateTime());
                    organizer.setUser(user);

                    Event event = new Event();
                    event.setId(resultSet.getLong("event_id"));
                    event.setTitle(resultSet.getString("title"));
                    event.setDescription(resultSet.getString("description"));
                    event.setLocation(resultSet.getString("location"));
                    event.setStartDateTime(resultSet.getTimestamp("start_date_time").toLocalDateTime());
                    event.setEndDateTime(resultSet.getTimestamp("end_date_time").toLocalDateTime());
                    event.setTotalTickets(resultSet.getInt("total_tickets"));
                    event.setTicketsSold(resultSet.getInt("tickets_sold"));
                    event.setTicketPrice(resultSet.getDouble("ticket_price"));
                    event.setCategory(resultSet.getString("category"));
                    event.setStatus(resultSet.getString("status"));
                    event.setCreated_at(resultSet.getTimestamp("event_created_at").toLocalDateTime());
                    event.setOrganizer(organizer);
                    events.add(event);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    @Override
    public Event updateEvent(Event event) {
         String updateQuery="UPDATE events SET "
                 + "title = COALESCE(?, title), "
                 + "description = COALESCE(?, description), "
                 + "location = COALESCE(?, location), "
                 + "start_date_time = COALESCE(?, start_date_time), "
                 + "end_date_time = COALESCE(?, end_date_time), "
                 + "ticket_price = COALESCE(?, ticket_price), "
                 + "total_tickets = CASE WHEN ? >= tickets_sold THEN ? ELSE total_tickets END, "
                 + "category = COALESCE(?, category), "
                 + "status = COALESCE(?, status) "
                 + "WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, event.getTitle());
            preparedStatement.setString(2, event.getDescription());
            preparedStatement.setString(3, event.getLocation());

            if (event.getStartDateTime() != null) {
                preparedStatement.setTimestamp(4, Timestamp.valueOf(event.getStartDateTime()));
            } else {
                preparedStatement.setNull(4, Types.TIMESTAMP);
            }

            if (event.getEndDateTime() != null) {
                preparedStatement.setTimestamp(5, Timestamp.valueOf(event.getEndDateTime()));
            } else {
                preparedStatement.setNull(5, Types.TIMESTAMP);
            }

            preparedStatement.setDouble(6, event.getTicketPrice());

            preparedStatement.setInt(7, event.getTotalTickets());
            preparedStatement.setInt(8, event.getTotalTickets());

            preparedStatement.setString(9, event.getCategory());
            preparedStatement.setString(10, event.getStatus());

            preparedStatement.setLong(11, event.getId());

            int rows = preparedStatement.executeUpdate();
            if(rows==0){
                throw new SQLException("No updates were made");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return event;
    }

    @Override
    public void deleteEvent(Long id) {
        String deleteEventQuery="DELETE FROM events WHERE id=?";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(deleteEventQuery)
                ){
            preparedStatement.setLong(1,id);
            int deleted= preparedStatement.executeUpdate();
            if (deleted==0){
                throw new SQLException("Resource not created");
            }
            System.out.println("Resource deleted.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTicketsSold(Long eventId, int incrementBy) {
        System.out.println("Ticket updating is under construction");
    }

    @Override
    public void updateEventStatus(Long eventId, String status) {
        String updateStatusEventQuery="UPDATE FROM events SET" +
                "status=?" +
                " WHERE id=?";
        try(
                Connection connection=DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement=connection.prepareStatement(updateStatusEventQuery)
        ){
            preparedStatement.setString(1,status);
            preparedStatement.setLong(2,eventId);
            int updatedStatus= preparedStatement.executeUpdate();
            if (updatedStatus==0){
                throw new SQLException("Event status not updated");
            }
            System.out.println("Event status has been changed.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
