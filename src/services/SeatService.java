package services;

import entities.Seat;
import repositories.interfaces.SeatRepository;
import java.util.List;

public class SeatService {
    private final SeatRepository repo;

    public SeatService(SeatRepository repo) {
        this.repo = repo;
    }

    public boolean createSeat(Seat seat) {
        if (seat.getSeatNumber() == null || seat.getSeatNumber().isEmpty()) {
            throw new IllegalArgumentException("Seat number cannot be empty");
        }
        if (seat.getVenueId() <= 0) {
            throw new IllegalArgumentException("Invalid venue ID");
        }
        return repo.create(seat);
    }

    public Seat getSeatById(int id) {
        Seat seat = repo.findById(id);
        if (seat == null) {
            throw new RuntimeException("Seat with ID " + id + " not found");
        }
        return seat;
    }

    public List<Seat> getAllSeats() {
        return repo.findAll();
    }

    public List<Seat> getSeatsByVenue(int venueId) {
        if (venueId <= 0) {
            throw new IllegalArgumentException("Invalid venue ID");
        }
        return repo.findByVenueId(venueId);
    }

    public boolean reserveSeat(int seatId) {
        if (!repo.isSeatAvailable(seatId)) {
            throw new RuntimeException("Seat is already booked or unavailable");
        }
        return repo.updateStatus(seatId, "RESERVED");
    }

    public boolean releaseSeat(int seatId) {
        return repo.updateStatus(seatId, "AVAILABLE");
    }

    public boolean isSeatAvailable(int seatId) {
        return repo.isSeatAvailable(seatId);
    }
}