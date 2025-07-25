package tickets.sevices;

import events.entity.Event;
import tickets.entity.Ticket;
import users.user.entity.Vendor;

import java.util.List;

public interface TicketsDao {
    Ticket createTicketsForEvent(Event event, int quantity);
    void assignTicketsToVendor(List<Long> ticketIds, Vendor vendor);
    Ticket markTicketSold(Long ticketId, Long attendeeId);
    List<Ticket>getTicketsByEvent(Long eventId);
    List<Ticket> getTicketsByVendor(Long vendorId);
    List<Ticket> getAvailableTickets(String ticketStatus);
    /*
    * Phase 2
    * tickets by seat
    * tickets by QR code
    * tickets by checked in
    * tickets by blockchainTokenId
    * */
}
