package services;

import entities.Venue;
import repositories.interfaces.VenueRepository;
import java.util.List;

public class VenueService {
    private final VenueRepository repo;

    public VenueService(VenueRepository repo) {
        this.repo = repo;
    }

    public boolean createVenue(Venue venue) {
        if (venue.getName() == null || venue.getName().isEmpty()) {
            throw new IllegalArgumentException("Venue name cannot be empty");
        }
        if (venue.getAddress() == null || venue.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Venue address cannot be empty");
        }
        return repo.create(venue);
    }

    public Venue getVenueById(int id) {
        Venue venue = repo.findById(id);
        if (venue == null) {
            throw new RuntimeException("Venue with ID " + id + " not found");
        }
        return venue;
    }

    public List<Venue> getAllVenues() {
        return repo.findAll();
    }

    public boolean deleteVenue(int id) {
        return repo.delete(id);
    }
}