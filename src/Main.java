import data.interfaces.IDB;
import data.postgres.PostgresDB;

import entities.*;
import repositories.interfaces.*;
import repositories.impl.*;
import services.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== Event Ticketing System Demo ===");

        try {
            // 1) DB via interface
            IDB db = new PostgresDB();

            // 2) Repositories via interfaces
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
            int venueId = venues.get(venues.size() - 1).getId(); // берем последний

            System.out.println("2) Creating event...");
            eventService.createEvent(new Event("Rock Concert", "2026-03-15", venueId));

            List<Event> events = eventService.getAllEvents();
            if (events.isEmpty()) throw new RuntimeException("No events found after insert.");
            int eventId = events.get(events.size() - 1).getId();

            System.out.println("3) Creating seats...");
            // Создаём места. В твоей модели Seat хранит venueId (как у тебя было раньше)
            seatService.createSeat(new Seat("A1", venueId));
            seatService.createSeat(new Seat("A2", venueId));

            // ❗ У нас нет метода getAllSeatsByVenue, поэтому:
            //  - либо берем seatId из базы через новый метод,
            //  - либо временно проверяем наличие через простую проверку ID.
            //
            // Сейчас делаем самый безопасный вариант без новых методов:
            // Проверим доступность seat с id=1 (или поменяй на реальный seat id который есть у тебя).
            int seatIdToCheck = 1;

            System.out.println("4) Making reservation...");
            // reservation: (eventId, seatId, customerName)
            reservationService.createReservation(new Reservation(eventId, seatIdToCheck, "John Doe"));

            System.out.println("\n5) All Events:");
            for (Event e : eventService.getAllEvents()) {
                System.out.println("- " + e.getId() + " " + e.getName() + " on " + e.getDate());
            }

            System.out.println("\n6) Seat availability:");
            // Твой SeatService ожидает 1 аргумент
            System.out.println("Seat " + seatIdToCheck + ": " +
                    (seatService.isSeatAvailable(seatIdToCheck) ? "AVAILABLE" : "BOOKED"));

            System.out.println("\n=== Demo completed successfully ===");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
