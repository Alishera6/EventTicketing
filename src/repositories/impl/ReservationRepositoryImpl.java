package repositories.impl;

import data.interfaces.IDB;
import entities.Reservation;
import repositories.interfaces.ReservationRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryImpl implements ReservationRepository {

    private final IDB db;

    public ReservationRepositoryImpl(IDB db) {
        this.db = db;
    }

    @Override
    public boolean create(Reservation reservation) {

        String sql = "insert into reservations(event_id, seat_id, customer_name, ticket_type, price) values (?, ?, ?, ?, ?)";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, reservation.getEventId());
            st.setInt(2, reservation.getSeatId());
            st.setString(3, reservation.getCustomerName());
            st.setString(4, reservation.getTicketType());
            st.setDouble(5, reservation.getPrice());

            st.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error creating reservation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reservation findById(int id) {

        String sql = "select * from reservations where id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Reservation> findAll() {

        List<Reservation> list = new ArrayList<>();
        String sql = "select * from reservations";

        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Reservation> findByEventId(int eventId) {

        List<Reservation> list = new ArrayList<>();
        String sql = "select * from reservations where event_id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, eventId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Reservation> findByCustomer(String customerName) {

        List<Reservation> list = new ArrayList<>();
        String sql = "select * from reservations where customer_name = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, customerName);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean cancelReservation(int id) {

        String sql = "delete from reservations where id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            return st.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean existsForSeatAndEvent(int seatId, int eventId) {

        String sql = "select count(*) as cnt from reservations where seat_id = ? and event_id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, seatId);
            st.setInt(2, eventId);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("cnt") > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // helper mapper
    private Reservation map(ResultSet rs) throws SQLException {
        return new Reservation(
                rs.getInt("id"),
                rs.getInt("event_id"),
                rs.getInt("seat_id"),
                rs.getString("customer_name"),
                rs.getString("ticket_type"),
                rs.getDouble("price")
        );
    }
}
