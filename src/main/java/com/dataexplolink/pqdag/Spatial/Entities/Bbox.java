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
public interface Bbox {
    public double xmax();
    public double ymax();
    public double xmin();
    public double ymin();
    public double distanceTp(Bbox bbox);
    public double distanceTp(Geometry geometry);
    public boolean intersects(Bbox bbox);
}
