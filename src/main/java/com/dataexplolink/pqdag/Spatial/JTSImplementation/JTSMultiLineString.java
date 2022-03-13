/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataexplolink.pqdag.Spatial.JTSImplementation;

import com.dataexplolink.pqdag.Spatial.Entities.Bbox;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;

import java.io.Serializable;

/**
 *
 * @author hyousfi
 */
public class JTSMultiLineString implements JTSGeometry,com.dataexplolink.pqdag.Spatial.Entities.MultiLineString, Serializable {
    
    private MultiLineString jts_multiLine;

    public JTSMultiLineString(com.dataexplolink.pqdag.Spatial.Entities.LineString[] lineStrings) {
        LineString[] jts_lines = new LineString[lineStrings.length];
        for (int i = 0; i < lineStrings.length; i++) {
            jts_lines[i] = (LineString) ((JTSLineString) lineStrings[i]).getJtsGeo();
        }
        jts_multiLine = new GeometryFactory().createMultiLineString(jts_lines);
        
    }

    public JTSMultiLineString(MultiLineString jts_multiLine) {
        this.jts_multiLine = jts_multiLine;
    }

    @Override
    public Geometry getJtsGeo() {
        return this.jts_multiLine;
    }

    @Override
    public double distanceTo(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.jts_multiLine.distance(((JTSGeometry) g).getJtsGeo());
    }

    @Override
    public Bbox bbox() {
        return new JTSBbox(this.jts_multiLine.getEnvelopeInternal());    
    }
    
    @Override
    public String toString() {
        return this.jts_multiLine.toString();
    }

    @Override
    public boolean within(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.jts_multiLine.within(((JTSGeometry)g).getJtsGeo());
    }
    
    @Override
    public boolean disjoint(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.jts_multiLine.disjoint(((JTSGeometry)g).getJtsGeo());
    }

    @Override
    public boolean intersects(com.dataexplolink.pqdag.Spatial.Entities.Geometry g) {
        return this.jts_multiLine.intersects(((JTSGeometry)g).getJtsGeo());
    }
    
    
}
