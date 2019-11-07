package backend;

import java.sql.*;

public class AdminController {
    Connection con;

    public boolean update (String table, String primaryKey, String attributes, String newValues) {
        connect();
        String[] attributeQueries = attributes.split(",");
        String[] newValueQueries = newValues.split(",");
        String pk = "";

        if (table.toLowerCase() == "customer") {
            pk = "customer_id";
        } else if (table.toLowerCase() == "driver") {
            pk = "driver_id";
        } else if (table.toLowerCase() == "travel") {
            pk = "travel_id";
        } else if (table.toLowerCase() == "customertravel") {
            pk = "booking_id";
        }
        else if(table.toLowerCase() == "city"){
            pk = "city_name";
        }

        if (pk == "") {
            return false;
        }

        if(pk.equals("city_name")) {

            System.out.println("we do this one");
            for (int i = 0; i < attributeQueries.length; i++) {
                try {
                    PreparedStatement statement = con.prepareStatement("UPDATE " + table + " SET "+ attributeQueries[i] + " = ? WHERE "+ pk + " = ?");
                    statement.setString(1, newValueQueries[i]);
                    statement.setString(2, primaryKey);
                    statement.execute();

                } catch (SQLException e) {
                    e.printStackTrace();
                    disconnect();
                    return false;
                }
            }
        }
        else {
            for (int i = 0; i < attributeQueries.length; i++) {
                try {
                    int primKey = Integer.parseInt(primaryKey);
                    PreparedStatement statement = con.prepareStatement("UPDATE " + table + " SET "+ attributeQueries[i] + " = ? WHERE "+ pk + " = ?");
                    statement.setString(1, newValueQueries[i]);
                    statement.setInt(2, primKey);
                    statement.execute();

                } catch (SQLException e) {
                    e.printStackTrace();
                    disconnect();
                    return false;
                }
            }
        }
        disconnect();
        return true;
    }


    public String showInfo (String table) {
        connect();
        StringBuffer result = new StringBuffer();
        System.out.println( table);

        if (table.toLowerCase() == "customer") {
            try {
                PreparedStatement statement = con.prepareStatement("SELECT * FROM " + table);
                ResultSet res = statement.executeQuery();
                while (res.next()) {
                    result.append("customer_id: " + res.getInt(1) + " customer_fname: "
                            + res.getString(2) + " customer_lname: "
                            + res.getString(3) + " customer_address: "
                            + res.getString(4) + " customer_email: "
                            + res.getString(5) + " customer_phonenumber: "
                            + res.getString(6) + " customer_zipcode: "
                            + res.getInt(7) + " customer_city:"
                            + res.getString(8) + "\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                disconnect();
            }
        } else if (table.toLowerCase() == "driver") {
            try {
                PreparedStatement statement = con.prepareStatement("SELECT * FROM " + table);
                ResultSet res = statement.executeQuery();
                while (res.next()) {
                    result.append("driver_id: " + res.getInt(1) + " driver_personnumber: "
                            + res.getString(2) + " driver_fname: "
                            + res.getString(3) + " driver_lname: "
                            + res.getString(4) + " driver_address: "
                            + res.getString(5) + " driver_telephonenumber: "
                            + res.getString(6) + " driver_zipcode: "
                            + res.getInt(7) + " driver_city:"
                            + res.getString(8) + "\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                disconnect();
            }
        } else if (table.toLowerCase() == "travel") {
            try {
                PreparedStatement statement = con.prepareStatement("SELECT * FROM " + table);
                ResultSet res = statement.executeQuery();
                while (res.next()) {
                    result.append("travel_id: " + res.getInt(1) + " travel_from: "
                            + res.getString(2) + " travel_to: "
                            + res.getString(3) + " travel_departure: "
                            + res.getTimestamp(4) + " travel_arrival: "
                            + res.getTimestamp(5) + " travel_price: "
                            + res.getInt(6) + " travel_seatsavailable: "
                            + res.getInt(6) + " travel_driverid: "
                            + res.getInt(7) + "\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                disconnect();
            }
        } else if (table.toLowerCase() == "customertravel") {
            try {
                PreparedStatement statement = con.prepareStatement("SELECT * FROM " + table);
                ResultSet res = statement.executeQuery();
                while (res.next()) {
                    result.append("booking_id: " + res.getInt(1) + " customer_id: "
                            + res.getInt(2) + " travel_id: "
                            + res.getInt(3) + " nbr_of_seats_booked: "
                            + res.getInt(7) + "\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                disconnect();
            }
        }
        disconnect();
        return result.toString();
    }

    public boolean delete(String table, String primaryKey) {
        connect();
        String pk = "";

        if (table.toLowerCase() == "customer") {
            pk = "customer_id";
        } else if (table.toLowerCase() == "driver") {
            pk = "driver_id";
        } else if (table.toLowerCase() == "travel") {
            pk = "travel_id";
        } else if (table.toLowerCase() == "customertravel") {
            pk = "booking_id";
        }

        if (pk == "") {
            return false;
        }

        try {
            PreparedStatement statement = con.prepareStatement("DELETE FROM " + table + " WHERE LOWER(?) = LOWER(?)");
            statement.setString(1, pk);
            statement.setString(2, primaryKey);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            disconnect();
            return false;
        }

        disconnect();
        return true;
    }



    public void connect() {
        try {
            Class.forName("org.postgresql.Driver").newInstance();

            con = DriverManager.getConnection("jdbc:postgresql://pgserver.mah.se/traveldata_grp1_nov6?user=aj0739&password=6lg2f7p2");
        } catch (Exception e) {
            try {
                con.close();
            } catch (SQLException ex) { }
        }
    }

    public void disconnect() {
        try {
            con.close();
        } catch (Exception e) {
            System.exit(0);
        }
    }

}
