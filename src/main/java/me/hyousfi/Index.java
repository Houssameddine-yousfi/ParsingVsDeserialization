package me.hyousfi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;



public class Index {

    public static final String DB_URL = "";

    public static Connection createConnection() throws SQLException {
        Connection connection = null;
        String url = "jdbc:sqlite:strings.db";

        connection = DriverManager.getConnection(url);
        System.out.println("connection is working");

        return connection;
    }

    public static void saveStringsToDB(){
        Connection connection = null;
        try {
            connection = createConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

       String sql = "INSERT INTO strings(wkt) VALUES(?)";

        try {
            FileInputStream geo_file = new FileInputStream("AerialwayThing.txt");
            Scanner sc = new Scanner(geo_file);

            connection.setAutoCommit(false);
            while (sc.hasNextLine()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                String line = sc.nextLine();
                statement.setString(1,line);
                statement.executeUpdate();
            }
            connection.commit();
            connection.close();
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){




        //save geo objects as JTSs in sqlite
            //use JTS parser for this
            //use my parser also to compare
        //run a filter using the parsing
        //urn a filter using the deserialization
    }
}
