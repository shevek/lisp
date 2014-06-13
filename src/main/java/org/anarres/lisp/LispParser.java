/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.lisp;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Longs;
import java.io.EOFException;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import javax.annotation.CheckForSigned;
import javax.annotation.Nonnull;
import static org.anarres.lisp.LispRuntime.*;

/**
 *
 * @author shevek
 */
public class LispParser {

    // private static final Log LOG = LogFactory.getLog(LispParser.class);
    private final PushbackReader reader;

    public LispParser(@Nonnull String text) {
        this(new StringReader(text));
    }

    public LispParser(@Nonnull Reader reader) {
        this(reader instanceof PushbackReader ? ((PushbackReader) reader) : new PushbackReader(reader, 10));
    }

    public LispParser(@Nonnull PushbackReader reader) {
        this.reader = reader;
    }

    @CheckForSigned
    private int readCharStart() throws IOException {
        for (;;) {
            int c = reader.read();
            if (Character.isWhitespace(c))
                continue;
            return c;
        }
    }

    @CheckForSigned
    private int readCharNext() throws IOException {
        for (;;) {
            int c = reader.read();
            if (Character.isWhitespace(c))
                return -1;
            if (c == '(' || c == ')' || c == '.') {
                reader.unread(c);
                return -1;
            }
            return c;
        }
    }

    @Nonnull
    private LispToken readToken() throws IOException {
        int c = readCharStart();
        switch (c) {
            case -1:
                return LispToken.TOK_EOF;
            case '(':
                return LispToken.TOK_OPEN;
            case ')':
                return LispToken.TOK_CLOSE;
            case '.':
                return LispToken.TOK_DOT;
        }
        StringBuilder buf = new StringBuilder();
        buf.append((char) c);
        for (;;) {
            int d = readCharNext();
            if (d == -1)
                break;
            buf.append((char) d);
        }
        String text = buf.toString();
        Number l = Longs.tryParse(text);
        if (l != null)
            return new LispToken(LispToken.TYPE_NUMBER, l);
        Double d = Doubles.tryParse(text);
        if (d != null)
            return new LispToken(LispToken.TYPE_NUMBER, d);
        return new LispToken(LispToken.TYPE_IDENTIFIER, new LispIdentifier(text));
    }

    /**
     * Parses an expression.
     *
     * @return An expression which may be passed as the second argument to {@link LispInterpreter#eval(org.anarres.lisp.LispCons, java.lang.Object)}.
     * @throws IOException 
     */
    public Object readExpression() throws IOException {
        Object stack = null;
        Object list = null;

        for (;;) {
            LispToken token = readToken();
            switch (token.type) {
                case LispToken.TYPE_EOF:
                    throw new EOFException();
                case LispToken.TYPE_OPEN:
                    stack = cons(list, stack);
                    list = null;
                    // LOG.info("push: " + stack);
                    break;
                case LispToken.TYPE_CLOSE:
                    // LOG.info("close: " + stack);
                    Object value = reverse(list);
                    list = car(stack);
                    list = cons(value, list);
                    stack = cdr(stack);
                    if (!consp(stack))
                        return value;
                    // LOG.info("pop: " + stack);
                    break;
                default:
                    if (!consp(stack))
                        return token.value;
                    list = cons(token.value, list);
                    break;
            }
        }
    }
}
