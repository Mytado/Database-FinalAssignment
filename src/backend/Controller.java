package backend;

import java.sql.*;
import java.util.*;

public class Controller {

    public String search(String from, String to) throws Exception{
        StringBuffer result = new StringBuffer();

        String query = "SELECT travelId, travelFrom, travelTo, departure, arrival, price, seatsAvailable FROM Travel";
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

        Connection con = DriverManager.getConnection("jdbc:postgresql://pgserver.mah.se/anstdata?user=aj0533&password=5rsgoqk7");
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

    public static void main(String[] args){
        Controller c = new Controller();
        try{
            c.search("", "");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
