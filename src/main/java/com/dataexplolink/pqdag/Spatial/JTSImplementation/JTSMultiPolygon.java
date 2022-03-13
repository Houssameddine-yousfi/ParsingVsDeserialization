package com.dataexplolink.pqdag.Spatial.JTSImplementation;

import com.dataexplolink.pqdag.Spatial.Entities.Bbox;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hyousfi
 */
public class JTSMultiPolygon implements JTSGeometry,com.dataexplolink.pqdag.Spatial.Entities.MultiPolygon, Serializable {
    
    private MultiPolygon multi_poly;

    public JTSMultiPolygon(com.dataexplolink.pqdag.Spatial.Entities.Polygon[] polygons) {
        Polygon[] jts_polys = new Polygon[polygons.length];
        for (int i = 0; i < polygons.length; i++) {
            jts_polys[i] = (Polygon) ((JTSPolygon) polygons[i]).getJtsGeo();
        }
        multi_poly = new GeometryFactory().createMultiPolygon(jts_polys);
        
    }

    public JTSMultiPolygon(MultiPolygon multi_poly) {
        this.multi_poly = multi_poly;
    }
    
    @Override
    public Geometry getJtsGeo() {
        return this.multi_poly;
    }

    @Override
    public double distanceTo(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.multi_poly.distance(((JTSGeometry) g).getJtsGeo());
    }

    @Override
    public Bbox bbox() {
        return new JTSBbox(this.multi_poly.getEnvelopeInternal());
    }
    
    @Override
    public String toString() {
        return this.multi_poly.toString();
    }

    @Override
    public boolean within(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.multi_poly.within(((JTSGeometry)g).getJtsGeo());
    }
    
    @Override
    public boolean disjoint(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.multi_poly.disjoint(((JTSGeometry)g).getJtsGeo());
    }

    @Override
    public boolean intersects(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.multi_poly.intersects(((JTSGeometry)g).getJtsGeo());
    }
    
}
