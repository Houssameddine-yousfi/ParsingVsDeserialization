/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataexplolink.pqdag.Spatial.JTSImplementation;

import com.dataexplolink.pqdag.Spatial.Entities.Bbox;
import org.locationtech.jts.geom.Envelope;

/**
 *
 * @author hyousfi
 */
public class JTSBbox implements Bbox{

    private Envelope envlope;

    public JTSBbox(double x1, double y1, double x2, double y2) {
        envlope = new Envelope(x1, x2, y1, y2);
    }
    
    
    public JTSBbox(Envelope envlope) {
        this.envlope = envlope;
    }

    public Envelope getEnvlope() {
        return envlope;
    }

    public void setEnvlope(Envelope envlope) {
        this.envlope = envlope;
    }
    
    
    @Override
    public double xmax() {
        return envlope.getMaxX();
    }

    @Override
    public double ymax() {
        return envlope.getMaxY();
    }

    @Override
    public double xmin() {
        return envlope.getMinX();
    }

    @Override
    public double ymin() {
        return envlope.getMinY();
    }

    @Override
    public double distanceTp(Bbox bbox) {
        return this.envlope.distance(((JTSBbox) bbox).getEnvlope());
    }

    @Override
    public double distanceTp(com.dataexplolink.pqdag.Spatial.Entities.Geometry geometry) {
        Envelope env = ((JTSGeometry) geometry).getJtsGeo().getEnvelopeInternal();
        return this.envlope.distance(env);
    }

    @Override
    public String toString() {
        return "" + xmin() + "|" + ymin() + "|" + xmax() + "|" + ymax();
    }

    @Override
    public boolean intersects(Bbox bbox) {
        return this.envlope.intersects(((JTSBbox) bbox).getEnvlope());
    }

  
    
    
    
    
}
