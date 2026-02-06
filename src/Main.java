import data.postgres.PostgresDB;
import entities.*;
import repositories.impl.*;
import services.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            //Initialize all components
            PostgresDB db = new PostgresDB();

            //Initialize repositories
            EventRepositoryImpl eventRepo = new EventRepositoryImpl(db);
            VenueRepositoryImpl venueRepo = new VenueRepositoryImpl(db);
            SeatRepositoryImpl seatRepo = new SeatRepositoryImpl(db);
            ReservationRepositoryImpl reservationRepo = new ReservationRepositoryImpl(db);

            //Initialize services
            EventService eventService = new EventService(eventRepo);
            VenueService venueService = new VenueService(venueRepo);
            SeatService seatService = new SeatService(seatRepo);
            ReservationService reservationService = new ReservationService(reservationRepo, seatRepo);

            //Demo functionality
            System.out.println("=== Event Ticketing System Demo ===");

            //Create a venue
            System.out.println("\n1. Creating a venue...");
            Venue venue = new Venue("Concert Hall", "123 Music Street");
            venueService.createVenue(venue);

            //Create an event
            System.out.println("2. Creating an event...");
            Event event = new Event("Rock Concert", "2026-03-15", 1);
            eventService.createEvent(event);

            //Create some seats
            System.out.println("3. Creating seats...");
            Seat seat1 = new Seat("A1", 1);
            Seat seat2 = new Seat("A2", 1);
            seatService.createSeat(seat1);
            seatService.createSeat(seat2);

            //Make a reservation
            System.out.println("4. Making a reservation...");
            Reservation reservation = new Reservation(1, 1, "John Doe");
            reservationService.createReservation(reservation);

            //Display all events
            System.out.println("\n5. All Events:");
            List<Event> events = eventService.getAllEvents();
            for (Event e : events) {
                System.out.println("- " + e.getName() + " on " + e.getDate());
            }

            //Check seat availability
            System.out.println("\n6. Seat A1 availability: " +
                    (seatService.isSeatAvailable(1) ? "AVAILABLE" : "BOOKED"));

            System.out.println("\n=== Demo completed successfully ===");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}