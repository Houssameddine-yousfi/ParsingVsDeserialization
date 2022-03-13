/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataexplolink.pqdag.Spatial.JTSImplementation;

import com.dataexplolink.pqdag.Spatial.Entities.Bbox;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

import java.io.Serializable;


/**
 *
 * @author hyousfi
 */
public class JTSLineString implements JTSGeometry,com.dataexplolink.pqdag.Spatial.Entities.LineString , Serializable {

    private LineString linestring;

    public JTSLineString(Coordinate[] coordinates) {
        this.linestring = new GeometryFactory().createLineString(coordinates);
    }

    public JTSLineString(LineString linestring) {
        this.linestring = linestring;
    }
    
    @Override
    public Geometry getJtsGeo() {
        return linestring;
    }

    @Override
    public double distanceTo(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.linestring.distance(((JTSGeometry)g).getJtsGeo());
    }

    @Override
    public Bbox bbox() {
        return new JTSBbox(this.linestring.getEnvelopeInternal());
    }
    
    @Override
    public String toString() {
        return this.linestring.toString();
    }

    @Override
    public boolean within(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.linestring.within(((JTSGeometry)g).getJtsGeo());
    }

    @Override
    public boolean disjoint(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.linestring.disjoint(((JTSGeometry)g).getJtsGeo());
    }

    @Override
    public boolean intersects(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.linestring.intersects(((JTSGeometry)g).getJtsGeo());
    }
    
}
