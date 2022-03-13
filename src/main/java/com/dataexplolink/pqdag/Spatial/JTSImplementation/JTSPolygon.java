/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataexplolink.pqdag.Spatial.JTSImplementation;

import com.dataexplolink.pqdag.Spatial.Entities.Bbox;
import com.dataexplolink.pqdag.Spatial.Entities.Polygon;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import java.io.Serializable;

/**
 *
 * @author hyousfi
 */
public class JTSPolygon implements JTSGeometry,Polygon, Serializable {

    private org.locationtech.jts.geom.Polygon jts_polygon;

    
    public JTSPolygon(Coordinate[] coordinates) {
        jts_polygon = new GeometryFactory().createPolygon(coordinates);
    }
    
    public JTSPolygon(org.locationtech.jts.geom.Polygon jts_polygon) {
        this.jts_polygon = jts_polygon;
    }

   
    
    
    @Override
    public Geometry getJtsGeo() {
        return this.jts_polygon;
    }

    @Override
    public double distanceTo(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.jts_polygon.distance(((JTSGeometry)g).getJtsGeo());
    }

    @Override
    public Bbox bbox() {
        return new JTSBbox(this.jts_polygon.getEnvelopeInternal());
    }

    @Override
    public String toString() {
        return jts_polygon.toString();
    }

    @Override
    public boolean within(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.jts_polygon.within(((JTSGeometry)g).getJtsGeo());
    }
    
    @Override
    public boolean disjoint(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.jts_polygon.disjoint(((JTSGeometry)g).getJtsGeo());
    }

    @Override
    public boolean intersects(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.jts_polygon.intersects(((JTSGeometry)g).getJtsGeo());
    }
    
}
