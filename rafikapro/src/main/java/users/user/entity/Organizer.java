package users.user.entity;

import java.time.LocalDateTime;

public class Organizer {
    private Long id;
    private User user;
    private String businessNumber;
//    private List<Event> events; // optional for now
    private LocalDateTime createdAt;
    private String subscriptionTier; // future use
}
