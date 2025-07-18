package users.user.entity;

import java.time.LocalDateTime;

public class Organizer {
    private Long id;
    private User user;
    private String licenseNumber;
//    private List<Event> events; // optional for now
    private String companyName;
    private String contactPerson;
    private LocalDateTime createdAt;
    private String subscriptionTier; // future use

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getSubscriptionTier() {
        return subscriptionTier;
    }

    public void setSubscriptionTier(String subscriptionTier) {
        this.subscriptionTier = subscriptionTier;
    }

    @Override
    public String toString() {
        return "Organizer{" +
                "id=" + id +
                ", user=" + user.toString() +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", companyName='" + companyName + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", createdAt=" + createdAt +
                ", subscriptionTier='" + subscriptionTier + '\'' +
                '}';
    }
}
