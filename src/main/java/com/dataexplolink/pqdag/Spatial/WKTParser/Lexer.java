package com.dataexplolink.pqdag.Spatial.WKTParser;

import java.util.StringTokenizer;

/**
 *
 * @author hyousfi
 */
public class Lexer {

    public static final String DELIMITERS = " \t,|\"()<>\n";

    private String line;
    private StringTokenizer tokenizer;
    private boolean isEndOfLine = false;

    public Lexer(String line) {
        this.line = line;
        this.tokenizer = new StringTokenizer(line, DELIMITERS, true);
    }

    public Token nextToken() {
        while (tokenizer.hasMoreTokens()) {
            String strToken = tokenizer.nextToken();

            if (strToken.equals(" ") || strToken.equals("\t")) {
                continue;
            }

            if (strToken.equals(",")) {
                return new Token(Token.COMMA_TOKEN, strToken);
            }

            if (strToken.equals("|")) {
                return new Token(Token.BAR_TOKEN, strToken);
            }

            if (strToken.equals("<")) {
                return new Token(Token.LT_TOKEN, strToken);
            }

            if (strToken.equals(">")) {
                return new Token(Token.GT_TOKEN, strToken);
            }

            if (strToken.equals("\"")) {
                return new Token(Token.QUOAT_TOKEN, strToken);
            }

            if (strToken.equals("^^")) {
                return new Token(Token.DOUBLE_HEAD_TOKEN, strToken);
            }

            if (strToken.equals("(")) {
                return new Token(Token.OPEN_PAREN_TOKEN, strToken);
            }

            if (strToken.equals(")")) {
                return new Token(Token.CLOSE_PAREN_TOKEN, strToken);
            }

            if (strToken.startsWith("<") && strToken.endsWith(">")) {
                return new Token(Token.RESOURCE_TOKEN, strToken);
            }

            if (strToken.equals("\n")) {
                isEndOfLine = true;
                return new Token(Token.EOL_TOKEN, "");
            }

            return new Token(Token.UNKNOWN_TOKEN, strToken);

        }
        return new Token(Token.EOL_TOKEN, "");
    }

    public boolean hasMoreTokens() {
        if(!this.tokenizer.hasMoreTokens() && !isEndOfLine){
            isEndOfLine = true;
            return true;
        } else if (isEndOfLine){
            return false;
        }
        return true;
    }
}

