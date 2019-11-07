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

        if (pk == "") {
            return false;
        }

        for (int i = 0; i < attributeQueries.length; i++) {
            try {
                PreparedStatement statement = con.prepareStatement("UPDATE " + table + " SET ? = ? WHERE LOWER(?) = LOWER(?)");
                statement.setString(1, attributeQueries[i]);
                statement.setString(2, newValueQueries[i]);
                statement.setString(3, pk);
                statement.setString(4, primaryKey);
                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
                disconnect();
                return false;
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
                    result.append("Customer ID: " + res.getInt(1) + " First name: "
                            + res.getString(2) + " Last name: "
                            + res.getString(3) + " Address: "
                            + res.getString(4) + " Email: "
                            + res.getString(5) + " Phone number: "
                            + res.getString(6) + " Zip code: "
                            + res.getInt(7) + " City:"
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
                    result.append("Driver ID: " + res.getInt(1) + " Person number: "
                            + res.getString(2) + " First name: "
                            + res.getString(3) + " Last name: "
                            + res.getString(4) + " Address: "
                            + res.getString(5) + " Telephone number: "
                            + res.getString(6) + " Zip code: "
                            + res.getInt(7) + " City:"
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
                    result.append("Travel ID: " + res.getInt(1) + " From: "
                            + res.getString(2) + " To: "
                            + res.getString(3) + " Departure: "
                            + res.getTimestamp(4) + " Arrival: "
                            + res.getTimestamp(5) + " Price: "
                            + res.getInt(6) + " Seats Available: "
                            + res.getInt(6) + " Driver ID: "
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
                    result.append("Booking ID: " + res.getInt(1) + " Customer ID: "
                            + res.getInt(2) + " Travel ID: "
                            + res.getInt(3) + " Number of seats booked: "
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
