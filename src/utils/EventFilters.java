package utils;

import entities.Event;
import java.util.List;
import java.util.stream.Collectors;

public class EventFilters {

    public static List<Event> filterByName(List<Event> events, String keyword) {
        return events.stream()
                .filter(e -> e.getName() != null && e.getName().contains(keyword))
                .collect(Collectors.toList());
    }
}