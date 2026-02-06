import data.postgres.PostgresDB;

import entities.*;
import repositories.impl.*;
import repositories.interfaces.*;
import services.*;

import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== Event Ticketing System Demo ===");

        try {
            // 1) DB (Singleton)
            PostgresDB db = PostgresDB.getInstance();

            // 2) Repositories
            EventRepository eventRepo = new EventRepositoryImpl(db);
            VenueRepository venueRepo = new VenueRepositoryImpl(db);
            SeatRepository seatRepo = new SeatRepositoryImpl(db);
            ReservationRepository reservationRepo = new ReservationRepositoryImpl(db);

            // 3) Services
            EventService eventService = new EventService(eventRepo);
            VenueService venueService = new VenueService(venueRepo);
            SeatService seatService = new SeatService(seatRepo);
            ReservationService reservationService = new ReservationService(reservationRepo, seatRepo);

            // --- Demo flow ---
            System.out.println("\n1) Creating venue...");
            venueService.createVenue(new Venue("Concert Hall", "123 Music Street"));

            List<Venue> venues = venueService.getAllVenues();
            if (venues.isEmpty()) throw new RuntimeException("No venues found after insert.");
            int venueId = venues.get(venues.size() - 1).getId(); // last inserted

            System.out.println("2) Creating event...");
            // Builder usage (if you have it). If not, this line still works normally.
            Event event = new Event("Rock Concert", "2026-03-15", venueId);
            eventService.createEvent(event);

            List<Event> events = eventService.getAllEvents();
            if (events.isEmpty()) throw new RuntimeException("No events found after insert.");
            int eventId = events.get(events.size() - 1).getId(); // last inserted

            System.out.println("3) Creating seats...");
            seatService.createSeat(new Seat("A1", venueId));
            seatService.createSeat(new Seat("A2", venueId));

            // IMPORTANT: choose an existing seat id from DB.
            // Since you already have seats with ids 1,2,3 in Supabase, use 1.
            int seatIdToCheck = 1;

            System.out.println("4) Making reservation (Factory -> ticket price -> DB) ...");
            Reservation reservation = new Reservation(eventId, seatIdToCheck, "John Doe", "VIP", 0);

            boolean ok = reservationService.createReservation(reservation);
            System.out.println("Reservation created: " + ok);
            System.out.println("Ticket type: " + reservation.getTicketType() + ", price: " + reservation.getPrice());

            System.out.println("\n5) All Events (sorted by id using lambda):");
            eventService.getAllEvents().stream()
                    .sorted(Comparator.comparingInt(Event::getId))
                    .forEach(e -> System.out.println("- " + e.getId() + " " + e.getName() + " on " + e.getDate()));

            System.out.println("\n6) Seat availability:");
            System.out.println("Seat " + seatIdToCheck + ": " +
                    (seatService.isSeatAvailable(seatIdToCheck) ? "AVAILABLE" : "BOOKED"));

            System.out.println("\n=== Demo completed successfully ===");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
