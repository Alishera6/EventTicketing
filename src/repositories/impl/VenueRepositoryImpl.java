package repositories.impl;

import data.interfaces.IDB;
import entities.Venue;
import repositories.interfaces.VenueRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenueRepositoryImpl implements VenueRepository {
    private final IDB db;

    public VenueRepositoryImpl(IDB db) {
        this.db = db;
    }

    @Override
    public boolean create(Venue venue) {
        String sql = "insert into venues(name, address) values (?, ?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, venue.getName());
            st.setString(2, venue.getAddress());
            st.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ SQL Error in createVenue: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Venue findById(int id) {
        String sql = "select * from venues where id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new Venue(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address")
                );
            }
            return null;

        } catch (SQLException e) {
            System.out.println("❌ Error finding venue: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Venue> findAll() {
        List<Venue> venues = new ArrayList<>();
        String sql = "select * from venues";

        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                venues.add(new Venue(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address")
                ));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error getting all venues: " + e.getMessage());
            e.printStackTrace();
        }
        return venues;
    }

    @Override
    public boolean delete(int id) {
        String sql = "delete from venues where id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            int rows = st.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error deleting venue: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
