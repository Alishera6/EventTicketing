package entities;

public class Reservation {
    private int id;
    private int eventId;
    private int seatId;
    private String customerName;

    public Reservation(int id, int eventId, int seatId, String customerName) {
        this.id = id;
        this.eventId = eventId;
        this.seatId = seatId;
        this.customerName = customerName;
    }

    public Reservation(int eventId, int seatId, String customerName) {
        this.eventId = eventId;
        this.seatId = seatId;
        this.customerName = customerName;
    }

    public int getId() {
        return id;
    }

    public int getEventId() {
        return eventId;
    }

    public int getSeatId() {
        return seatId;
    }

    public String getCustomerName() {
        return customerName;
    }
}
