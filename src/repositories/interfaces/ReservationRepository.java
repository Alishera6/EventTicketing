package repositories.interfaces;

import entities.Reservation;
import java.util.List;

public interface ReservationRepository {
    boolean create(Reservation reservation);
    Reservation findById(int id);
    List<Reservation> findAll();
    List<Reservation> findByEventId(int eventId);
    List<Reservation> findByCustomer(String customerName);
    boolean cancelReservation(int id);
    boolean existsForSeatAndEvent(int seatId, int eventId);
}