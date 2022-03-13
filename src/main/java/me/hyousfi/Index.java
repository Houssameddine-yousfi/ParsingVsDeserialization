package me.hyousfi;

import com.dataexplolink.pqdag.Spatial.DependencyManager;
import com.dataexplolink.pqdag.Spatial.Entities.GeoFactory;
import com.dataexplolink.pqdag.Spatial.Entities.Geometry;
import com.dataexplolink.pqdag.Spatial.Exceptions.WKTParsingException;
import org.locationtech.jts.geom.GeometryFactory;

import javax.sound.midi.Soundbank;
import java.io.*;
import java.sql.*;
import java.util.Scanner;



public class Index {

    public static final String DB_URL_STRINGS = "jdbc:sqlite:strings.db";
    public static final String DB_URL_BLOBS = "jdbc:sqlite:objects.db";
    public static final String STRINGS_FILE_PATJ = "AerialwayThing.txt";

    public static Connection createConnection(String db_url) throws SQLException {
        Connection connection = null;

        connection = DriverManager.getConnection(db_url);
        System.out.println("connection is working");

        return connection;
    }

    public static void saveStringsToDB(){
        Connection connection = null;
        try {
            connection = createConnection(DB_URL_STRINGS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

       String sql = "INSERT INTO strings(wkt) VALUES(?)";

        try {
            FileInputStream geo_file = new FileInputStream(STRINGS_FILE_PATJ);
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

    public static void saveObjectsToDb(){
        Connection connection = null;
        try {
            connection = createConnection(DB_URL_BLOBS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO strings(s_object) VALUES(?)";

        try {
            FileInputStream geo_file = new FileInputStream(STRINGS_FILE_PATJ);
            Scanner sc = new Scanner(geo_file);

            connection.setAutoCommit(false);
            Long start = System.currentTimeMillis();
            while (sc.hasNextLine()) {

                PreparedStatement statement = connection.prepareStatement(sql);

                //Read and parse a line
                String line = sc.nextLine();
                String[] parts = line.split("\\|");
                //System.out.println(parts[4]);
                GeoFactory factory = DependencyManager.getGeometryFactory();
                Geometry g = factory.parseWKT(parts[4].substring(1));

                //serialize the geo object
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bos);
                out.writeObject(g);
                out.flush();

                //use the byte array in the statment and execute it
                statement.setBytes(1,bos.toByteArray());
                statement.executeUpdate();
            }
            Long end = System.currentTimeMillis();
            System.out.println("the process took :" + (end - start));
            connection.commit();
            connection.close();
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        } catch (WKTParsingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]){

        int i=0,j=0;

        // Connection to database with strings
        Connection connection2 = null;
        try {
            connection2 = createConnection(DB_URL_STRINGS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Initialization of the query and the geo factory
        String select_all_query2 = "Select wkt from strings";
        GeoFactory factory = DependencyManager.getGeometryFactory();

        try {
            // Running the query
            Statement statement = connection2.createStatement();
            ResultSet rs = statement.executeQuery(select_all_query2);

            //Fetching the results
            long start = System.currentTimeMillis();
            while (rs.next()){

                //Getting the line from the result set
                String line = rs.getString("wkt");

                //the line contain some prefix seperated with "|" this is why we parse the last part
                String[] parts = line.split("\\|");
                Geometry g = factory.parseWKT(parts[4].substring(1));

                //just a check if g is not null to force JVM to parse things
                if (g != null)
                    j++;
            }

            //calculating and printing execution time
            long end = System.currentTimeMillis();
            long parsing_time = end - start;
            System.out.println("Parsing of " + j + " objects in " + parsing_time + " ms");

            //closing the statment and the connection
            statement.close();
            connection2.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (WKTParsingException e) {
            e.printStackTrace();
        }


        // Connection to database with strings
        Connection connection = null;
        try {
            connection = createConnection(DB_URL_BLOBS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Initialization of the query and
        String select_all_query = "Select s_object from strings";

        try {
            // Running the query
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(select_all_query);

            //Fetching the results
            long start = System.currentTimeMillis();
            while (rs.next()){

                //getting the bytes from the result set
                byte[] bytes = rs.getBytes("s_object");

                //using the Object inputs stream to deserilize the objects
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                ObjectInputStream in = new ObjectInputStream(bis);
                Geometry g = (Geometry) in.readObject();
                //System.out.println(g);

                //just a check if g is not null to force JVM to serelize things
                if (g != null)
                    i++;
            }

            //calculating and printing execution time
            long end = System.currentTimeMillis();
            long sereialization_time = end - start;
            System.out.println("De-serialisation of " + i + " objects in " + sereialization_time + " ms");

            //closing the statment and the connection
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
