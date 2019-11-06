package backend;

import java.sql.*;

public class AdminController {
    Connection con;

    public void update (String table, String primaryKey, String attributes, String newValues) {
        connect();
        String[] attributeQueries = attributes.split(",");
        String[] newValueQueries = newValues.split(",");

        for (int i = 0; i < attributeQueries.length; i++) {
            try {
                PreparedStatement statement = con.prepareStatement("UPDATE  + table SET" + attributeQueries[i] + "=" + newValueQueries[i]);
                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        disconnect();
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
