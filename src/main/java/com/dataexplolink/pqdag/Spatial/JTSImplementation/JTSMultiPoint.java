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
import org.locationtech.jts.geom.MultiPoint;

import java.io.Serializable;


/**
 *
 * @author hyousfi
 */
public class JTSMultiPoint implements JTSGeometry,com.dataexplolink.pqdag.Spatial.Entities.MultiPoint, Serializable {

    private MultiPoint jts_multipoint;

    public JTSMultiPoint(Coordinate[] coordinates) {
        jts_multipoint = new GeometryFactory().createMultiPointFromCoords(coordinates);
    }

    public JTSMultiPoint(MultiPoint jts_multipoint) {
        this.jts_multipoint = jts_multipoint;
    }
    
    
    
    @Override
    public Geometry getJtsGeo() {
        return this.jts_multipoint;
    }

    @Override
    public double distanceTo(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.jts_multipoint.distance(((JTSGeometry) g).getJtsGeo());
    }

    @Override
    public Bbox bbox() {
        return new JTSBbox(this.jts_multipoint.getEnvelopeInternal());
    }
    
    @Override
    public String toString() {
        return this.jts_multipoint.toString();
    }

    @Override
    public boolean within(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.jts_multipoint.within(((JTSGeometry)g).getJtsGeo());
    }
    
    @Override
    public boolean disjoint(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.jts_multipoint.disjoint(((JTSGeometry)g).getJtsGeo());
    }

    @Override
    public boolean intersects(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.jts_multipoint.intersects(((JTSGeometry)g).getJtsGeo());
    }
    
}
