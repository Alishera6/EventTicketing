package entities;

public class Event {
    private int id;
    private String name;
    private String date;
    private int venueId;

    public Event(int id, String name, String date, int venueId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.venueId = venueId;
    }

    public Event(String name, String date, int venueId) {
        this.name = name;
        this.date = date;
        this.venueId = venueId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getVenueId() {
        return venueId;
    }
}
