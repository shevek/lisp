/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.lisp;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * A set of basic runtime utilities for internal use only.
 *
 * @author shevek
 */
public class LispRuntime {

    public static boolean consp(@CheckForNull Object in) {
        return in instanceof LispCons;
    }

    public static boolean symbolp(@CheckForNull Object in) {
        return in instanceof LispIdentifier;
    }

    @Nonnull
    public static LispCons cons(@CheckForNull Object car, @CheckForNull Object cdr) {
        return new LispCons(car, cdr);
    }

    @CheckForNull
    public static Object car(@CheckForNull Object in) {
        return ((LispCons) in).car;
    }

    @CheckForNull
    public static Object cdr(@CheckForNull Object in) {
        return ((LispCons) in).cdr;
    }

    // Nonnull if args are given.
    public static LispCons list(@Nonnull Object... in) {
        LispCons out = null;
        for (int i = in.length - 1; i >= 0; i--)
            out = cons(in[i], out);
        return out;
    }

    @CheckForNull
    public static LispCons reverse(@CheckForNull Object in) {
        if (in == null)
            return null;
        if (!consp(in))
            throw new ClassCastException("Not a list: " + in);
        LispCons out = null;
        while (consp(in)) {
            out = cons(car(in), out);
            in = cdr(in);
        }
        return out;
    }
}
