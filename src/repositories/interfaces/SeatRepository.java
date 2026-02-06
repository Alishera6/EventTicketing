package repositories.interfaces;

import entities.Seat;
import java.util.List;

public interface SeatRepository {
    boolean create(Seat seat);
    Seat findById(int id);
    List<Seat> findAll();
    List<Seat> findByVenueId(int venueId);
    boolean updateStatus(int seatId, String status);
    boolean isSeatAvailable(int seatId);
}