package factory;

import tickets.*;

public class TicketFactory {
    public static Ticket createTicket(String type) {
        return switch (type.toUpperCase()) {
            case "VIP" -> new VipTicket();
            case "STUDENT" -> new StudentTicket();
            default -> new StandardTicket();
        };
    }
}