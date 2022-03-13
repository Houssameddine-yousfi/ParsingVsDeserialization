/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataexplolink.pqdag.Spatial.Entities;

/**
 *
 * @author hyousfi
 */
public interface Geometry {
    
    public double distanceTo(Geometry g);
    public Bbox bbox();
    public boolean within(Geometry g);
    public boolean disjoint(Geometry g);
    public boolean intersects(Geometry g);
    
    //public abstract double sqrDistanceTo(Point p);
    //public abstract double sqrDistanceTo(Linestring p);
    //public abstract double sqrDistanceTo(Polygon p);

    
    
}
