package events.service;

import events.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

public class EventsDaoImpl implements EventsDao{
    @Override
    public Event createEvent(Event event) {
        return null;
    }

    @Override
    public Event getEventById(Long id) {
        return null;
    }

    @Override
    public List<Event> getAllEvents() {
        return null;
    }

    @Override
    public List<Event> getEventsByOrganizer(Long organizerId) {
        return null;
    }

    @Override
    public List<Event> getEventsByCategory(String category) {
        return null;
    }

    @Override
    public List<Event> getEventsByStatus(String status) {
        return null;
    }

    @Override
    public Event updateEvent(Event event) {
         /*
         **What event organizer can update
         * 
         String title;
         String description;
         String location;
         LocalDateTime startDateTime;
         LocalDateTime endDateTime;
         int totalTickets;
         int ticketsSold;
         double ticketPrice; 
         String category; 
         String status;
         * 
         */
        return null;
    }

    @Override
    public void deleteEvent(Long id) {

    }

    @Override
    public void updateTicketsSold(Long eventId, int incrementBy) {

    }

    @Override
    public void updateEventStatus(Long eventId, String status) {

    }
}
