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
        String sql = "insert into reservations(event_id, seat_id, customer_name) values (?, ?, ?)";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, reservation.getEventId());
            st.setInt(2, reservation.getSeatId());
            st.setString(3, reservation.getCustomerName());
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
                return new Reservation(
                        rs.getInt("id"),
                        rs.getInt("event_id"),
                        rs.getInt("seat_id"),
                        rs.getString("customer_name")
                );
            }
            return null;

        } catch (SQLException e) {
            System.out.println("Error finding reservation: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "select * from reservations";

        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("id"),
                        rs.getInt("event_id"),
                        rs.getInt("seat_id"),
                        rs.getString("customer_name")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error getting all reservations: " + e.getMessage());
            e.printStackTrace();
        }

        return reservations;
    }

    @Override
    public List<Reservation> findByEventId(int eventId) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "select * from reservations where event_id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, eventId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("id"),
                        rs.getInt("event_id"),
                        rs.getInt("seat_id"),
                        rs.getString("customer_name")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error getting reservations by event: " + e.getMessage());
            e.printStackTrace();
        }

        return reservations;
    }

    @Override
    public List<Reservation> findByCustomer(String customerName) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "select * from reservations where customer_name = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, customerName);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("id"),
                        rs.getInt("event_id"),
                        rs.getInt("seat_id"),
                        rs.getString("customer_name")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error getting reservations by customer: " + e.getMessage());
            e.printStackTrace();
        }

        return reservations;
    }

    @Override
    public boolean cancelReservation(int reservationId) {
        // Самый простой cancel: удалить запись.
        String sql = "delete from reservations where id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, reservationId);
            int rows = st.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error canceling reservation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean existsForSeatAndEvent(int seatId, int eventId) {
        // Никакого status — просто проверяем, есть ли уже бронь на это место для этого события
        String sql = "select count(*) as cnt from reservations where seat_id = ? and event_id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, seatId);
            st.setInt(2, eventId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt("cnt") > 0;
            }
            return false;

        } catch (SQLException e) {
            System.out.println("Error checking reservation existence: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
