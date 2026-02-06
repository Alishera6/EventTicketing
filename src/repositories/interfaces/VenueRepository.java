package repositories.interfaces;

import entities.Venue;
import java.util.List;

public interface VenueRepository {
    boolean create(Venue venue);
    Venue findById(int id);
    List<Venue> findAll();
    boolean delete(int id);
}