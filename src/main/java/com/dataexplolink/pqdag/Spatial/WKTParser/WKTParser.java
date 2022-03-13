package com.dataexplolink.pqdag.Spatial.WKTParser;

import com.dataexplolink.pqdag.Spatial.Entities.*;
import com.dataexplolink.pqdag.Spatial.Exceptions.WKTParsingException;
import com.qdag.interaction.Pair;

import java.util.ArrayList;
import java.util.Iterator;

public class WKTParser implements Iterator<Pair<Double,Double>> {

    private Lexer lexer;
    private GeoFactory factory = null;
    private Geometry created_geo = null;
    private boolean parsing_error = false;
    private Token currentToken;

    private Pair<Double,Double> currentCoordinates = null;


    public WKTParser(GeoFactory factory) {
        this.factory = factory;
    }

    public Geometry parse(String line) throws WKTParsingException {
        this.lexer = new Lexer(line);
        Token first = nextToken();

        switch (first.getVal()){
            case "POINT":
                parsePoint();
                break;
            case "LINESTRING":
                parseLineString();
                break;
            case "POLYGON":
                parsePolygon();
                break;
            case "MULTIPOINT":
                parseMultiPoint();
                break;
            case "MULTILINESTRING":
                parseMultiLineString();
                break;
            case "MULTIPOLYGON":
                parseMultiPolygon();
                break;
            default:
                this.parsing_error = true;
                break;
        }

        if(parsing_error)
            throw new WKTParsingException("Error while parsing " + line);
        return created_geo;
    }

    private void parseMultiLineString() {

        match(Token.OPEN_PAREN_TOKEN);
        ArrayList<LineString> lineStrings = new ArrayList<>();

        while (currentToken.getType() != Token.EOL_TOKEN){
            parseLineString();
            LineString line = (LineString) created_geo;
            lineStrings.add(line);
            Token t = nextToken();
            if(t.getType() != Token.COMMA_TOKEN)
                break;
        }

        created_geo = factory.createMultiLineString(lineStrings.toArray(new LineString[0]));
    }

    private void parseMultiPolygon() {
        match(Token.OPEN_PAREN_TOKEN);
        ArrayList<Polygon> polygons = new ArrayList<>();

        while (currentToken.getType() != Token.EOL_TOKEN){
            parsePolygon();
            Polygon polygon = (Polygon) created_geo;
            polygons.add(polygon);
            match(Token.CLOSE_PAREN_TOKEN);
            Token t = nextToken();
            if(t.getType() != Token.COMMA_TOKEN)
                break;
        }

        created_geo = factory.createMultiPolygon(polygons.toArray(new Polygon[0]));
    }


    private void parseMultiPoint() {
        short state = 0;
        double x = 0,y = 0;
        Token t = currentToken;
        ArrayList<Point> points = new ArrayList<>();

        while (this.currentToken.getType() != Token.EOL_TOKEN){
            switch (state){
                case 0:
                    match(Token.OPEN_PAREN_TOKEN);
                    state = 1;
                    break;
                case 1:
                    t = nextToken();
                    if (t.getType() == Token.OPEN_PAREN_TOKEN){
                        state =  2;
                    } else if (t.getType() == Token.UNKNOWN_TOKEN){
                        x = Double.parseDouble(t.getVal());
                        state = 3;
                    } else  state = -1;
                    break;
                case 2:
                    t = nextToken();
                    if (t.getType() == Token.UNKNOWN_TOKEN){
                        x = Double.parseDouble(t.getVal());
                        state = 3;
                    } else  state = -1;
                    break;
                case 3:
                    t = match(Token.UNKNOWN_TOKEN);
                    y = Double.parseDouble(t.getVal());
                    Point p = this.factory.createPoint( x,y);
                    points.add(p);
                    state = 4;
                    break;
                case 4:
                    t = nextToken();
                    if(t.getType() == Token.CLOSE_PAREN_TOKEN)
                        state = 5;
                    else if(t.getType() == Token.COMMA_TOKEN)
                        state = 1;
                    else state = -1;
                    break;
                case 5:
                    t = nextToken();
                    if(t.getType() == Token.COMMA_TOKEN)
                        state = 1;
                    else if (t.getType() == Token.CLOSE_PAREN_TOKEN || t.getType() == Token.EOL_TOKEN)
                        state = 6;
                    else
                        state = -1;
                    break;
                case 6:
                    nextToken();
                    break;
                default:
                    parsing_error = true;
            }
        }

        this.created_geo = factory.createMultiPoint(points.toArray(new Point[0]));
    }

    private Token nextToken(){
        this.currentToken = lexer.nextToken();
        return this.currentToken;
    }

    private Token match(short tokenType){
        Token token = nextToken();
        if (token.getType() == tokenType)
            return token;

        parsing_error = true;
        return null;
    }

    private void parseLineString(){
        match(Token.OPEN_PAREN_TOKEN);
        this.created_geo = this.factory.createLineString(this);
        return;
    }

    private void parsePolygon(){
        match(Token.OPEN_PAREN_TOKEN);
        match(Token.OPEN_PAREN_TOKEN);
        this.created_geo = this.factory.createPolygon(this);
        return;
    }

    private void parsePoint(){
        match(Token.OPEN_PAREN_TOKEN);
        Token x_token = match(Token.UNKNOWN_TOKEN);
        Token y_token = match(Token.UNKNOWN_TOKEN);
        Double x = Double.parseDouble(x_token.getVal());
        Double y = Double.parseDouble(y_token.getVal());
        this.created_geo = this.factory.createPoint(x,y);
        return;
    }

    @Override
    public boolean hasNext() {
        if (currentToken.getType() == Token.CLOSE_PAREN_TOKEN)
            return false;

        Token t1 = match(Token.UNKNOWN_TOKEN);
        if(t1 == null)
            return false;

        Token t2 = match(Token.UNKNOWN_TOKEN);
        if (t2 == null )
            return false;

        Token t3 = nextToken();
        if (t3.getType() != Token.COMMA_TOKEN && t3.getType() != Token.CLOSE_PAREN_TOKEN) {
            parsing_error = true;
            return false;
        }


        this.currentCoordinates = new Pair<>(Double.parseDouble(t1.getVal()),Double.parseDouble(t2.getVal()));
        return true;
    }

    @Override
    public Pair<Double, Double> next() {
        return this.currentCoordinates;
    }
}
