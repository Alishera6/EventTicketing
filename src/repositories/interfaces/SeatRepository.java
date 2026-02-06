package repositories.interfaces;

import entities.Seat;
import java.util.List;

public interface SeatRepository extends Repository<Seat> {

    List<Seat> findByVenueId(int venueId);

    boolean isSeatAvailable(int seatId);
}
