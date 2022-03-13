package me.hyousfi;

import com.dataexplolink.pqdag.Spatial.DependencyManager;
import com.dataexplolink.pqdag.Spatial.Entities.GeoFactory;
import com.dataexplolink.pqdag.Spatial.Entities.Geometry;
import com.dataexplolink.pqdag.Spatial.Exceptions.WKTParsingException;
import org.locationtech.jts.geom.GeometryFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

        //save geo objects as JTSs in sqlite
            //use JTS parser for this
            //use my parser also to compare
        //run a filter using the parsing
        //urn a filter using the deserialization
    }
}
