package repositories.impl;

import data.interfaces.IDB;
import entities.Seat;
import repositories.interfaces.SeatRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatRepositoryImpl implements SeatRepository {
    private final IDB db;

    public SeatRepositoryImpl(IDB db) {
        this.db = db;
    }

    @Override
    public boolean create(Seat seat) {
        String sql = "insert into seats(seat_number, venue_id, status) values (?, ?, 'AVAILABLE')";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, seat.getSeatNumber());
            st.setInt(2, seat.getVenueId());
            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error creating seat: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Seat findById(int id) {
        String sql = "select * from seats where id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Seat(
                        rs.getInt("id"),
                        rs.getString("seat_number"),
                        rs.getInt("venue_id")
                );
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error finding seat: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Seat> findAll() {
        List<Seat> seats = new ArrayList<>();
        String sql = "select * from seats";
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                seats.add(new Seat(
                        rs.getInt("id"),
                        rs.getString("seat_number"),
                        rs.getInt("venue_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all seats: " + e.getMessage());
        }
        return seats;
    }

    @Override
    public List<Seat> findByVenueId(int venueId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "select * from seats where venue_id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, venueId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                seats.add(new Seat(
                        rs.getInt("id"),
                        rs.getString("seat_number"),
                        rs.getInt("venue_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error finding seats by venue: " + e.getMessage());
        }
        return seats;
    }

    @Override
    public boolean updateStatus(int seatId, String status) {
        String sql = "update seats set status = ? where id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, status);
            st.setInt(2, seatId);
            int rows = st.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating seat status: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isSeatAvailable(int seatId) {
        String sql = "select status from seats where id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, seatId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return "AVAILABLE".equals(rs.getString("status"));
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error checking seat availability: " + e.getMessage());
            return false;
        }
    }
}