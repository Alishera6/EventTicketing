package repositories.interfaces;

import entities.Event;
import java.util.List;

public interface EventRepository {
    boolean create(Event event);
    Event findById(int id);
    List<Event> findAll();
}
