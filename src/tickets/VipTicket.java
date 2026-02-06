package tickets;

public class VipTicket implements Ticket {
    public String getType() { return "VIP"; }
    public double getPrice() { return 200.0; }
}