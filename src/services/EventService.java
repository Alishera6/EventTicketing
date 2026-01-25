package services;

import entities.Event;
import repositories.interfaces.EventRepository;

import java.util.List;

public class EventService {

    private final EventRepository repo;

    public EventService(EventRepository repo) {
        this.repo = repo;
    }

    public boolean createEvent(Event event) {
        if (event.getName() == null || event.getName().isEmpty()) {
            throw new RuntimeException("Event name cannot be empty");
        }
        return repo.create(event);
    }

    public Event getEventById(int id) {
        Event event = repo.findById(id);
        if (event == null) {
            throw new RuntimeException("Event not found");
        }
        return event;
    }

    public List<Event> getAllEvents() {
        return repo.findAll();
    }
}
