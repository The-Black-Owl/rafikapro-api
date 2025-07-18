package users.user.service.organizer_service;

import users.user.entity.Organizer;

import java.util.List;

public interface OrganizerDAO {
    Organizer createOrganizer(Organizer organizer);
    Organizer updateOrganizer(Organizer organizer);
    void deleteOrganizer(Long id);
    List<Organizer> getAllOrganizers();
    Organizer getOrganizerById(Long id);
    Organizer getOrganizerByBusinessNumber(String licenseNumber);
    List<Organizer> getOrganizerBySubscription(String subscription);
}
