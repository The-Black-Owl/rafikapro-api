package tickets.entity;

import events.entity.Event;
import users.user.entity.Vendor;

import java.time.LocalDateTime;

public class Ticket {
    private Long id;
    private Event event;
    private Vendor vendor;
//    private User currentOwner; // if resale
    private String ticketNumber;
    private double price;
    private String status;
    private LocalDateTime soldAt;
    private boolean isPrinted;
    private LocalDateTime createdAt;
//    private String seatNumber; // optional
//    private String qrCodeUrl;  // optional
//private String blockchainTokenId; // link to Hedera NFT or SOL or Eth

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(LocalDateTime soldAt) {
        this.soldAt = soldAt;
    }

    public boolean isPrinted() {
        return isPrinted;
    }

    public void setPrinted(boolean printed) {
        isPrinted = printed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
