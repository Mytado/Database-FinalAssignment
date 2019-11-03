package backend;

import java.sql.*;
import java.util.*;

public class Controller {

    public String search(String from, String to) throws Exception{
        StringBuffer result = new StringBuffer();

        String query = "SELECT travel_id, travel_from, travel_to, travel_departure, travel_arrival, travel_price, travel_seatsAvailable FROM Travel";
        if(from.length() > 0 || to.length() > 0) {
            query += " WHERE ";
        }
        if(from.length() > 0){
            query += "travelFrom = " + from;
        }
        if(from.length() > 0 && to.length() > 0){
            query += " AND ";
        }
        if(to.length() > 0){
            query += "travelTo = " + to;
        }

        Class.forName("org.postgresql.Driver").newInstance();

        Connection con = DriverManager.getConnection("jdbc:postgresql://pgserver.mah.se/traveldata_grp1_nov6?user=aj0533&password=5rsgoqk7");
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet res = statement.executeQuery();
        while(res.next()){
            result.append(res.getInt(1) + " "
                    + res.getString(2) + " "
                    + res.getString(3) + " "
                    + res.getDate(4) + " "
                    + res.getDate(5) + " "
                    + res.getInt(6) + " "
                    + res.getInt(7) + "\n");
        }
        con.close();
        return result.toString();
    }

    public void createAccount(String fname, String lname, String address, int zipcode, String city, String email, String phoneNumber) throws Exception{
        Class.forName("org.postgresql.Driver").newInstance();

        Connection con = DriverManager.getConnection("jdbc:postgresql://pgserver.mah.se/traveldata_grp1_nov6?user=aj0533&password=5rsgoqk7");
        PreparedStatement statement = con.prepareStatement("INSERT INTO Customer (customer_fname, customer_lname, customer_address, customer_zipcode, customer_city, customer_email, customer_phoneNumber) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, fname);
        statement.setString(2, lname);
        statement.setString(3, address);
        statement.setInt(4, zipcode);
        statement.setString(5, city);
        statement.setString(6, email);
        statement.setString(7, phoneNumber);
        ResultSet res = statement.executeQuery();

        con.close();
    }

    public void adminShowUsers() throws Exception{
        Class.forName("org.postgresql.Driver").newInstance();

        Connection con = DriverManager.getConnection("jdbc:postgresql://pgserver.mah.se/traveldata_grp1_nov6?user=aj0533&password=5rsgoqk7");
        PreparedStatement statement = con.prepareStatement("SELECT * FROM Customer");
        ResultSet res = statement.executeQuery();
        while(res.next()){
            System.out.println(res.getInt(1) + " " + res.getString(2) + " " + res.getString(3) + " " + res.getString(4));
        }
        con.close();
    }

    public boolean login(String email) throws Exception{
        Class.forName("org.postgresql.Driver").newInstance();

        Connection con = DriverManager.getConnection("jdbc:postgresql://pgserver.mah.se/traveldata_grp1_nov6?user=aj0533&password=5rsgoqk7");
        PreparedStatement statement = con.prepareStatement("SELECT email FROM Customer WHERE email = " + email);
        ResultSet res = statement.executeQuery();
        con.close();
        String result = res.getString(1);
        return (result.length() > 0);
    }

    public boolean book(int travelId, int seats)throws Exception {
        Class.forName("org.postgresql.Driver").newInstance();

        Connection con = DriverManager.getConnection("jdbc:postgresql://pgserver.mah.se/traveldata_grp1_nov6?user=aj0533&password=5rsgoqk7");
        PreparedStatement statement = con.prepareStatement("SELECT availableSeats FROM Travel WHERE travelId = " + travelId);
        ResultSet res = statement.executeQuery();
        int availableSeats = res.getInt(1);
        if(seats <= availableSeats){
            PreparedStatement updateStatement = con.prepareStatement("UPDATE Travel SET availableSeats = availableSeats - " + seats);
            con.close();
            return true;
        }

        con.close();
        return false;
    }

    public static void main(String[] args){
        Controller c = new Controller();
        try{
            c.createAccount("Erik", "testsson", "testgatan 1", 27614, "Malmoe", "test@test.se", "782346238746");
            c.adminShowUsers();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
