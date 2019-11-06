package backend;

import java.sql.*;

public class AdminController {
    Connection con;

    public boolean update (String table, String primaryKey, String attributes, String newValues) {
        connect();
        String[] attributeQueries = attributes.split(",");
        String[] newValueQueries = newValues.split(",");
        String pk = "";
        boolean resOk = true;

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
            resOk = false;
            return resOk;
        }

        for (int i = 0; i < attributeQueries.length; i++) {
            try {
                PreparedStatement statement = con.prepareStatement("UPDATE  + table SET" + attributeQueries[i] + "=" + newValueQueries[i] + "WHERE" + pk + "=" + primaryKey);
                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
                disconnect();
                return false;
            }
        }
        disconnect();
        resOk = true;
        return resOk;
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
