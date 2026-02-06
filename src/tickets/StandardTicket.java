package tickets;

public class StandardTicket implements Ticket {
    public String getType() { return "STANDARD"; }
    public double getPrice() { return 100.0; }
}