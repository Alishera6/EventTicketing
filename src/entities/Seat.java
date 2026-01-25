package entities;

public class Seat {
    private int id;
    private String seatNumber;
    private int venueId;

    public Seat(int id, String seatNumber, int venueId) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.venueId = venueId;
    }

    public Seat(String seatNumber, int venueId) {
        this.seatNumber = seatNumber;
        this.venueId = venueId;
    }

    public int getId() {
        return id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public int getVenueId() {
        return venueId;
    }
}
