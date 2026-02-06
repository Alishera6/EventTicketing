package services;

import entities.Reservation;
import factory.TicketFactory;
import tickets.Ticket;
import repositories.interfaces.ReservationRepository;
import repositories.interfaces.SeatRepository;

public class ReservationService {

    private final ReservationRepository reservationRepo;
    private final SeatRepository seatRepo;

    public ReservationService(ReservationRepository reservationRepo, SeatRepository seatRepo) {
        this.reservationRepo = reservationRepo;
        this.seatRepo = seatRepo;
    }

    public boolean createReservation(Reservation reservation) {

        if (!seatRepo.isSeatAvailable(reservation.getSeatId())) {
            throw new RuntimeException("Seat is not available");
        }

        // FACTORY usage
        Ticket ticket = TicketFactory.createTicket(reservation.getTicketType());

        reservation.setPrice(ticket.getPrice());

        return reservationRepo.create(reservation);
    }
}
