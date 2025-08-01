package users.user.entity;

import java.time.LocalDateTime;

public class Vendor {
    private Long vendorId;
    private User user; // if linking to User
    private String tradingNumber;
//    private List<Ticket> allocatedTickets; // Optional for Phase 1
    private int ticketsSold;
    private String subscriptionTier;
    private String vendorType;
    private LocalDateTime createdAt;

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTradingNumber() {
        return tradingNumber;
    }

    public void setTradingNumber(String tradingNumber) {
        this.tradingNumber = tradingNumber;
    }

    public String getSubscriptionTier() {
        return subscriptionTier;
    }

    public void setSubscriptionTier(String subscriptionTier) {
        this.subscriptionTier = subscriptionTier;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vendorId=" + vendorId +
                ", user=" + user.getName() +
                ", tradingNumber='" + tradingNumber + '\'' +
                ", ticketsSold=" + ticketsSold +
                ", subscriptionTier='" + subscriptionTier + '\'' +
                ", vendorType='" + vendorType + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
