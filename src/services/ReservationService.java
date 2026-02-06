package services;

import entities.Reservation;
import repositories.interfaces.ReservationRepository;
import repositories.interfaces.SeatRepository;
import java.util.List;

public class ReservationService {
    private final ReservationRepository reservationRepo;
    private final SeatRepository seatRepo;

    public ReservationService(ReservationRepository reservationRepo, SeatRepository seatRepo) {
        this.reservationRepo = reservationRepo;
        this.seatRepo = seatRepo;
    }

    public boolean createReservation(Reservation reservation) {
        // Validate input
        if (reservation.getCustomerName() == null || reservation.getCustomerName().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        if (reservation.getEventId() <= 0) {
            throw new IllegalArgumentException("Invalid event ID");
        }
        if (reservation.getSeatId() <= 0) {
            throw new IllegalArgumentException("Invalid seat ID");
        }

        // Check if seat is available
        if (!seatRepo.isSeatAvailable(reservation.getSeatId())) {
            throw new RuntimeException("Seat is not available");
        }

        // Check if seat is already reserved for this event
        if (reservationRepo.existsForSeatAndEvent(reservation.getSeatId(), reservation.getEventId())) {
            throw new RuntimeException("Seat is already reserved for this event");
        }

        // Reserve the seat
        seatRepo.updateStatus(reservation.getSeatId(), "RESERVED");

        // Create reservation
        return reservationRepo.create(reservation);
    }

    public Reservation getReservationById(int id) {
        Reservation reservation = reservationRepo.findById(id);
        if (reservation == null) {
            throw new RuntimeException("Reservation with ID " + id + " not found");
        }
        return reservation;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }

    public List<Reservation> getReservationsByEvent(int eventId) {
        if (eventId <= 0) {
            throw new IllegalArgumentException("Invalid event ID");
        }
        return reservationRepo.findByEventId(eventId);
    }

    public List<Reservation> getReservationsByCustomer(String customerName) {
        if (customerName == null || customerName.isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        return reservationRepo.findByCustomer(customerName);
    }

    public boolean cancelReservation(int reservationId) {
        Reservation reservation = reservationRepo.findById(reservationId);
        if (reservation == null) {
            throw new RuntimeException("Reservation not found");
        }

        // Release the seat
        seatRepo.updateStatus(reservation.getSeatId(), "AVAILABLE");

        // Cancel reservation
        return reservationRepo.cancelReservation(reservationId);
    }
}