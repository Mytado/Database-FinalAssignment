package backend;

import java.sql.*;
import java.util.*;

public class Controller {
    Connection con;

    public void connect() {
        try {
            Class.forName("org.postgresql.Driver").newInstance();

            con = DriverManager.getConnection("jdbc:postgresql://pgserver.mah.se/traveldata_grp1_nov6?user=aj0739&password=6lg2f7p2");
        } catch (Exception e) {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String search(String from, String to) {
        StringBuffer result = new StringBuffer();

        String query = "SELECT travel_id, travel_from, travel_to, travel_departure, travel_arrival, travel_price, travel_seatsAvailable FROM Travel";
        if (from.length() > 0 || to.length() > 0) {
            query += " WHERE ";
        }
        if (from.length() > 0) {
            query += "travel_From = " + "'" + from + "'";
        }
        if (from.length() > 0 && to.length() > 0) {
            query += " AND ";
        }
        if (to.length() > 0) {
            query += "travel_To = " + "'" + to + "'";
        }
        query += " ORDER BY travel_id";

        connect();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                result.append("Travel ID: " + res.getInt(1) + " From: "
                        + res.getString(2) + " To: "
                        + res.getString(3) + " Departure: "
                        + res.getDate(4) + " Arrival: "
                        + res.getDate(5) + " Price: "
                        + res.getInt(6) + " seats Available: "
                        + res.getInt(7) + "\n");
            }
        }catch (Exception e){
            disconnect();
        }
        disconnect();
        return result.toString();
    }

    public void createAccount(String fname, String lname, String address, int zipcode, String city, String email, String phoneNumber) {
        connect();
        try {
            PreparedStatement statement = con.prepareStatement("INSERT INTO Customer (customer_fname, customer_lname, customer_address, customer_zipcode, customer_city, customer_email, customer_phoneNumber) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, fname);
            statement.setString(2, lname);
            statement.setString(3, address);
            statement.setInt(4, zipcode);
            statement.setString(5, city);
            statement.setString(6, email);
            statement.setString(7, phoneNumber);
            statement.execute();
        } catch(Exception e){
            disconnect();
        }
        disconnect();


    }

    public void adminShowUsers() {
        connect();
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Customer");
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                System.out.println(res.getInt(1) + " " + res.getString(2) + " " + res.getString(3) + " " + res.getString(4));
            }
        }catch(Exception e){
            disconnect();
        }
        disconnect();

    }

    public boolean login(String email)  {
        connect();
        try {
            PreparedStatement statement = con.prepareStatement("SELECT customer_email FROM Customer WHERE customer_email = ?");
            statement.setString(1, email);
            ResultSet res = statement.executeQuery();
            String result = "";
            while (res.next()) {
                result = res.getString(1);
            }
            disconnect();
            return (result.length() > 0);
        } catch(Exception e){
            disconnect();
        }
        return false;
    }

    public boolean book(int travelId, int seats, String email) {

        connect();
        try {
        PreparedStatement statement = con.prepareStatement("SELECT travel_seatsAvailable FROM Travel WHERE travel_id = " + travelId);
        ResultSet res = statement.executeQuery();
        int availableSeats = 0;
        while (res.next()) {
            availableSeats = res.getInt(1);
        }
        if (seats <= availableSeats) {
            PreparedStatement beginStatement = con.prepareStatement("BEGIN");
            beginStatement.execute();

            PreparedStatement updateStatement = con.prepareStatement("UPDATE Travel SET travel_seatsAvailable = travel_seatsAvailable - " + seats + "WHERE travel_id = " + travelId);
            updateStatement.execute();

            PreparedStatement selectStatement = con.prepareStatement("SELECT customer_id FROM customer WHERE customer_email = ?");
            selectStatement.setString(1, email);
            ResultSet customerResult = selectStatement.executeQuery();
            int customerId = 0;
            while (customerResult.next()) {
                customerId = customerResult.getInt(1);
            }

            PreparedStatement bookingStatement = con.prepareStatement("INSERT INTO Booking(customer_id, travel_id, nbr_of_seats_booked) VALUES(?,?,?)");
            bookingStatement.setInt(1, customerId);
            bookingStatement.setInt(2, travelId);
            bookingStatement.setInt(3, seats);
            bookingStatement.execute();

            PreparedStatement commitStatement = con.prepareStatement("COMMIT");
            commitStatement.execute();
            disconnect();
            return true;
        }
        }catch (Exception e){
            disconnect();
        }
        disconnect();

        return false;
    }

    public void disconnect() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Controller c = new Controller();
        try {
            //   c.createAccount("Erik", "testsson", "testgatan 1", 27614, "Malmoe", "test@test.se", "782346238746");
            System.out.println(c.book(1, 1, "Rick@gmail.com"));
            c.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            c.disconnect();
        }

    }
}
