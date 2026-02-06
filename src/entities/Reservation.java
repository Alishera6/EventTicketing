package entities;

public class Reservation {

    private int id;
    private int eventId;
    private int seatId;
    private String customerName;
    private String ticketType;
    private double price;

    public Reservation(int eventId, int seatId, String customerName, String ticketType, double price) {
        this.eventId = eventId;
        this.seatId = seatId;
        this.customerName = customerName;
        this.ticketType = ticketType;
        this.price = price;
    }

    public Reservation(int id, int eventId, int seatId, String customerName, String ticketType, double price) {
        this.id = id;
        this.eventId = eventId;
        this.seatId = seatId;
        this.customerName = customerName;
        this.ticketType = ticketType;
        this.price = price;
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

    public String getTicketType() {
        return ticketType;
    }

    public double getPrice() {
        return price;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

