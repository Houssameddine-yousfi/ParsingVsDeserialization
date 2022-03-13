/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataexplolink.pqdag.Spatial.Exceptions;

/**
 * Exception that raise while interpretation of the WKT
 * It can be string problem or a topology problem
 * @author hyousfi
 */
public class WKTParsingException extends Exception{

    public WKTParsingException(String message) {
        super(message);
    }

    
}
