/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataexplolink.pqdag.Spatial;

import com.dataexplolink.pqdag.Spatial.Entities.GeoFactory;
import com.dataexplolink.pqdag.Spatial.JTSImplementation.JTSGeoFactory;

/**
 * A class to abstract the creation of objects 
 * @author hyousfi
 */
public class DependencyManager {
    
    /**
     * Create a geometry factory
     * @return JTS implementation of the geometry factory
     */
    public static GeoFactory getGeometryFactory(){
        return new JTSGeoFactory();
    }
    

}
