package repositories.impl;

import data.interfaces.IDB;
import entities.Event;
import repositories.interfaces.EventRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventRepositoryImpl implements EventRepository {

    private final IDB db;

    public EventRepositoryImpl(IDB db) {
        this.db = db;
    }

    @Override
    public boolean create(Event event) {
        String sql = "insert into events(name, event_date, venue_id) values (?, ?, ?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, event.getName());
            st.setDate(2, Date.valueOf(event.getDate()));
            st.setInt(3, event.getVenueId());
            st.execute();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Event findById(int id) {
        String sql = "select * from events where id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new Event(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("event_date").toString(),
                        rs.getInt("venue_id")
                );
            }
            return null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        String sql = "select * from events";

        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                events.add(new Event(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("event_date").toString(),
                        rs.getInt("venue_id")
                ));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return events;
    }
}
