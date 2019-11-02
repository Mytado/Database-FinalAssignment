package backend;

import java.sql.*;
import java.util.*;

public class Controller {

    public String search(String to, String from) throws Exception{

        Class.forName("org.postgresql.Driver").newInstance();

        Connection con = DriverManager.getConnection("jdbc:postgresql://pgserver.mah.se/aj0533?user=aj0533&password=5rsgoqk7");
        PreparedStatement statement = con.prepareStatement("SELECT * FROM Projekt");
        ResultSet res = statement.executeQuery();
        while(res.next()){
            System.out.println(res.getInt(1) + " " + res.getString(2));
        }
        con.close();
        String result = "";

        return result;
    }

    public static void main(String[] args) throws Exception {
        Controller c = new Controller();
        c.search("", "");
    }
}
