/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataexplolink.pqdag.Spatial.JTSImplementation;

import com.dataexplolink.pqdag.Spatial.Entities.Bbox;
import com.dataexplolink.pqdag.Spatial.Entities.Geometry;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;

/**
 *
 * @author hyousfi
 */
public class JTSPoint implements com.dataexplolink.pqdag.Spatial.Entities.Point,JTSGeometry, Serializable {
    
    private Point jts_point;

    public JTSPoint(double x, double y) {
        jts_point = new GeometryFactory().createPoint(new Coordinate(x,y));
    }
    
    public JTSPoint(Point jts_point) {
        this.jts_point = jts_point;
    }

    @Override
    public double distanceTo(Geometry g) {
        //cast class to jts implementation then use the distance implementation
        return this.jts_point.distance(((JTSGeometry) g).getJtsGeo());
    }

    @Override
    public org.locationtech.jts.geom.Geometry getJtsGeo() {
        return jts_point;
    }

    @Override
    public void setx(double x) {
        jts_point.getCoordinate().setX(x);
    }

    @Override
    public void sety(double y) {
        jts_point.getCoordinate().setY(y);
    }

    @Override
    public Bbox bbox() {
        return new JTSBbox(this.jts_point.getEnvelopeInternal());
    }
    
    @Override
    public String toString() {
        return this.jts_point.toString();
    }

    @Override
    public boolean within(Geometry g) {
        return this.jts_point.within(((JTSGeometry)g).getJtsGeo());
    }
    
    @Override
    public boolean disjoint(Geometry g) {
        return this.jts_point.disjoint(((JTSGeometry)g).getJtsGeo());
    }

    @Override
    public boolean intersects(Geometry g) {
        return this.jts_point.intersects(((JTSGeometry)g).getJtsGeo());
    }
    
}
