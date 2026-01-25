import data.postgres.PostgresDB;
import entities.Event;
import repositories.impl.EventRepositoryImpl;
import services.EventService;

public class Main {
    public static void main(String[] args) {

        EventService service =
                new EventService(new EventRepositoryImpl(new PostgresDB()));

        try {
            service.createEvent(new Event("Concert", "2026-03-01", 1));

            for (Event e : service.getAllEvents()) {
                System.out.println(e.getId() + " " + e.getName());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
