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

    // ===== BUILDER STARTS HERE =====
    public static class Builder {
        private int id;
        private String name;
        private String date;
        private int venueId;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder venueId(int venueId) {
            this.venueId = venueId;
            return this;
        }

        public Event build() {
            return new Event(id, name, date, venueId);
        }
    }
}