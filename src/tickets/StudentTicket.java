package tickets;

public class StudentTicket implements Ticket {
    public String getType() { return "STUDENT"; }
    public double getPrice() { return 70.0; }
}
