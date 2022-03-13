/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataexplolink.pqdag.Spatial.JTSImplementation;

import com.dataexplolink.pqdag.Spatial.Entities.*;
import com.dataexplolink.pqdag.Spatial.Exceptions.WKTParsingException;
import com.dataexplolink.pqdag.Spatial.WKTParser.WKTParser;
import com.qdag.interaction.Pair;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Implementation of the abstract factory that make objects using JTS 
 * implementation
 * @author hyousfi
 */
public class JTSGeoFactory implements GeoFactory{

    @Override
    public Point createPoint(double x, double y) {
        return new JTSPoint(x, y);
    }

    @Override
    public Polygon createPolygon(Point[] points) {
        Coordinate coordinates[] = new Coordinate[points.length];
        for (int i = 0; i < points.length; i++) {
            coordinates[i] = ((JTSPoint) points[i]).getJtsGeo().getCoordinate();
        }
        return new JTSPolygon(coordinates);
    }



    @Override
    public LineString createLineString(Point[] points) {
        Coordinate coordinates[] = new Coordinate[points.length];
        for (int i=0; i<points.length; i++) {
            coordinates[i] = ((JTSPoint) points[i]).getJtsGeo().getCoordinate();
        }
        return new JTSLineString(coordinates);
    }

    @Override
    public MultiPoint createMultiPoint(Point[] points) {
        Coordinate coordinates[] = new Coordinate[points.length];
        for (int i=0; i<points.length; i++) {
            coordinates[i] = ((JTSPoint) points[i]).getJtsGeo().getCoordinate();
        }
        return new JTSMultiPoint(coordinates);
    }

    private ArrayList<Coordinate> getCoordiatesFromIterator(Iterator<Pair<Double, Double>> coordinatesIterator){
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        while (coordinatesIterator.hasNext()){
            Pair<Double, Double> entry = coordinatesIterator.next();
            Coordinate c = new Coordinate(entry.getT(),entry.getU());
            coordinates.add(c);
        }
        return coordinates;
    }

    @Override
    public LineString createLineString(Iterator<Pair<Double, Double>> coordinatesIterator) {
        ArrayList<Coordinate> coordinates = getCoordiatesFromIterator(coordinatesIterator);
        return new JTSLineString(coordinates.toArray(new Coordinate[0]));
    }

    @Override
    public Polygon createPolygon(Iterator<Pair<Double, Double>> coordinatesIterator) {
        ArrayList<Coordinate> coordinates = getCoordiatesFromIterator(coordinatesIterator);
        return new JTSPolygon(coordinates.toArray(new Coordinate[0]));
    }

    @Override
    public MultiLineString createMultiLineString(LineString[] lines) {
        return new JTSMultiLineString(lines);
    }

    @Override
    public MultiPolygon createMultiPolygon(Polygon[] polygons) {
        return new JTSMultiPolygon(polygons);
    }

//    @Override
//    public Geometry parseWKT(String wkt) throws WKTParsingException{
//        org.locationtech.jts.geom.Geometry g=null;
//        try {
//            // read a geometry from a WKT string (using the default geometry factory)
//            g = new WKTReader().read(wkt);
//            if(g == null){
//                System.out.println("wkt is :" + wkt);
//                throw new WKTParsingException("Error while parsing the WKT (g) is null");
//            }
//        } catch (ParseException ex) {
//            //Logger.getLogger(JTSGeoFactory.class.getName()).log(Level.SEVERE, null, ex);
//            throw new WKTParsingException("Error while parsing the WKT : " + wkt);
//        }
//        switch(g.getGeometryType()){
//            case "Point":
//                return new JTSPoint((org.locationtech.jts.geom.Point) g);
//            case "Polygon":
//                return new JTSPolygon((org.locationtech.jts.geom.Polygon) g);
//            case "LineString":
//                return new JTSLineString((org.locationtech.jts.geom.LineString) g);
//            case "MultiPoint":
//                return new JTSMultiPoint((org.locationtech.jts.geom.MultiPoint) g);
//            case "MultiPolygon":
//                return new JTSMultiPolygon((org.locationtech.jts.geom.MultiPolygon) g);
//            case "MultiLineString":
//                return new JTSMultiLineString((org.locationtech.jts.geom.MultiLineString) g);
//            default:
//                throw new WKTParsingException("unknown Geo type");
//        }
//    }

    @Override
    public Geometry parseWKT(String wkt) throws WKTParsingException{
        WKTParser parser = new WKTParser(this);
        return parser.parse(wkt);
    }


    @Override
    public Bbox createBbox(double x1, double y1, double x2, double y2) {
        return new JTSBbox(x1, y1, x2, y2);
    }
    
}
