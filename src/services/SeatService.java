package services;

import entities.Seat;
import repositories.interfaces.SeatRepository;

import java.util.List;

public class SeatService {
    private final SeatRepository repo;

    public SeatService(SeatRepository repo) {
        this.repo = repo;
    }

    public void createSeat(Seat seat) {
        if (seat.getSeatNumber() == null || seat.getSeatNumber().isEmpty()) {
            throw new RuntimeException("Seat number cannot be empty");
        }
        repo.save(seat);
    }

    public Seat getSeatById(int id) {
        Seat seat = repo.findById(id);
        if (seat == null) throw new RuntimeException("Seat not found");
        return seat;
    }

    public List<Seat> getAllSeats() {
        return repo.findAll();
    }

    public boolean isSeatAvailable(int seatId) {
        return repo.isSeatAvailable(seatId);
    }
}
