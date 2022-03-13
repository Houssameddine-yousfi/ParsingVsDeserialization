/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataexplolink.pqdag.Spatial.Entities;

import com.dataexplolink.pqdag.Spatial.Exceptions.WKTParsingException;
import com.qdag.interaction.Pair;

import java.util.Iterator;

/**
 * Interface for the abstract factory pattern
 * aka: this is the abstract factory
 * @author hyousfi
 */
public interface GeoFactory {
    
    public Point createPoint(double x, double y);
    public Polygon createPolygon(Point[] points);
    public Polygon createPolygon(Iterator<Pair<Double,Double>> coordinatesIterator);
    public LineString createLineString(Point[] points);
    public LineString createLineString(Iterator<Pair<Double,Double>> coordinatesIterator);
    public MultiPoint createMultiPoint(Point[] points);
    public MultiLineString createMultiLineString(LineString[] lines);
    public MultiPolygon createMultiPolygon(Polygon[] polygons);
    public Geometry parseWKT(String wkt) throws WKTParsingException;
    public Bbox createBbox(double x1, double y1, double x2, double y2);
    
}
