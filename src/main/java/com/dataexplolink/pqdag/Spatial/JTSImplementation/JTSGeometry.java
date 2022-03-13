/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataexplolink.pqdag.Spatial.JTSImplementation;

import com.dataexplolink.pqdag.Spatial.Entities.Geometry;

/**
 *
 * @author hyousfi
 */
public interface JTSGeometry extends Geometry{
    


    public org.locationtech.jts.geom.Geometry getJtsGeo();
    
    
    
}
