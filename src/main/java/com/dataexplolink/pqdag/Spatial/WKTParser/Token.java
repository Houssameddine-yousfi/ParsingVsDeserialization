package com.dataexplolink.pqdag.Spatial.WKTParser;

public class Token {

    public static final short EOL_TOKEN = 0;
    public static final short RESOURCE_TOKEN = 1;
    public static final short UNKNOWN_TOKEN = 2;
    public static final short QUOAT_TOKEN = 3;
    public static final short SPACE_TOKEN = 4;
    public static final short COMMA_TOKEN = 5;
    public static final short BAR_TOKEN = 6;
    public static final short OPEN_PAREN_TOKEN = 7;
    public static final short CLOSE_PAREN_TOKEN = 8;
    public static final short LT_TOKEN = 9;
    public static final short GT_TOKEN = 10;
    public static final short DOUBLE_HEAD_TOKEN = 11;

    private short type;
    private String val;

    public Token(short type, String val) {
        this.type = type;
        this.val = val;
    }

    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", val=\"" + val + "\"}";
    }

    public short getType() {
        return type;
    }

    public String getVal() {
        return val;
    }


}