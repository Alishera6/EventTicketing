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
    public void save(Seat seat) {
        String sql = "insert into seats(seat_number, venue_id) values (?, ?)";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, seat.getSeatNumber());
            st.setInt(2, seat.getVenueId());
            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("❌ SQL Error in saveSeat: " + e.getMessage());
            e.printStackTrace();
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
            System.out.println("❌ Error finding seat: " + e.getMessage());
            e.printStackTrace();
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
            System.out.println("❌ Error getting all seats: " + e.getMessage());
            e.printStackTrace();
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
            System.out.println("❌ Error finding seats by venue: " + e.getMessage());
            e.printStackTrace();
        }
        return seats;
    }

    @Override
    public boolean isSeatAvailable(int seatId) {
        // seat свободен, если нет записи в reservations
        String sql = "select count(*) as cnt from reservations where seat_id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, seatId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt("cnt") == 0;
            }
            return false;

        } catch (SQLException e) {
            System.out.println("❌ Error checking seat availability: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
