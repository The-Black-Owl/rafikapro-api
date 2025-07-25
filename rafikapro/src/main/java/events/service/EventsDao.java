package events.service;

import events.entity.Event;

import java.util.List;

public interface EventsDao {
    Event createEvent(Event event);

    Event getEventById(Long id);

    List<Event> getAllEvents();

    List<Event> getEventsByOrganizer(Long organizerId);

    List<Event> getEventsByCategory(String category);

    List<Event> getEventsByStatus(String status);

    Event updateEvent(Event event); // for edits

    void deleteEvent(Long id);

    void updateTicketsSold(Long eventId, int incrementBy);

    void updateEventStatus(Long eventId, String status);
}
