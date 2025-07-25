package tickets.sevices;

import events.entity.Event;
import tickets.entity.Ticket;
import users.user.entity.Vendor;

import java.util.List;

public class TicketsDaoImpl implements TicketsDao{
    @Override
    public Ticket createTicketsForEvent(Event event, int quantity) {
        return null;
    }

    @Override
    public void assignTicketsToVendor(List<Long> ticketIds, Vendor vendor) {

    }

    @Override
    public Ticket markTicketSold(Long ticketId, Long attendeeId) {
        return null;
    }

    @Override
    public List<Ticket> getTicketsByEvent(Long eventId) {
        return null;
    }

    @Override
    public List<Ticket> getTicketsByVendor(Long vendorId) {
        return null;
    }

    @Override
    public List<Ticket> getAvailableTickets(String ticketStatus) {
        return null;
    }
}
