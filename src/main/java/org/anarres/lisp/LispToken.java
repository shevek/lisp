/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.lisp;

import javax.annotation.CheckForNull;

/**
 * Only used in the parser to represent brackets, dots, etc.
 *
 * @author shevek
 */
/* pp */ class LispToken {

    public static final char TYPE_EOF = 0;
    public static final char TYPE_OPEN = '(';
    public static final char TYPE_CLOSE = ')';
    public static final char TYPE_DOT = '.';
    public static final char TYPE_NUMBER = '0';
    public static final char TYPE_STRING = '"';
    public static final char TYPE_IDENTIFIER = 'x';
    public static final LispToken TOK_EOF = new LispToken(TYPE_EOF, null);
    public static final LispToken TOK_OPEN = new LispToken(TYPE_OPEN, null);
    public static final LispToken TOK_CLOSE = new LispToken(TYPE_CLOSE, null);
    public static final LispToken TOK_DOT = new LispToken(TYPE_DOT, null);
    public final char type;
    public final Object value;

    public LispToken(char type, @CheckForNull Object value) {
        this.type = type;
        this.value = value;
    }
}
