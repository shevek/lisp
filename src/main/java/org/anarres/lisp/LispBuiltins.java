/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.lisp;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import static org.anarres.lisp.LispRuntime.*;

/**
 * Implementations of builtin functions for the interpreter.
 *
 * @author shevek
 */
public class LispBuiltins {

    public static boolean builtin_consp(@CheckForNull Object args) {
        return consp(car(args));
    }

    public static boolean builtin_symbolp(@CheckForNull Object args) {
        return symbolp(car(args));
    }

    @Nonnull
    public static LispCons builtin_cons(@CheckForNull Object in) {
        return new LispCons(car(in), car(cdr(in)));
    }

    @CheckForNull
    public static Object builtin_car(@CheckForNull Object args) {
        return car(car(args));
    }

    @CheckForNull
    public static Object builtin_cdr(@CheckForNull Object args) {
        return cdr(car(args));
    }

    @Nonnull
    public static LispCons builtin_list(@Nonnull Object in) {
        return (LispCons) in;
    }

    private interface Folder<T> {

        public T fold(T left, Object right);
    }

    private static <T> T fold(Folder<T> folder, T init, Object list) {
        T out = init;
        while (list != null) {
            out = folder.fold(out, car(list));
            list = cdr(list);
        }
        return out;
    }

    @Nonnull
    public static Object builtin_add(@CheckForNull Object in) {
        return fold(new Folder<Number>() {
            @Override
            public Number fold(Number left, Object right) {
                return left.doubleValue() + ((Number) right).doubleValue();
            }
        }, 0, in);
    }

    @Nonnull
    public static Object builtin_sub(@CheckForNull Object in) {
        return fold(new Folder<Number>() {
            @Override
            public Number fold(Number left, Object right) {
                return left.doubleValue() - ((Number) right).doubleValue();
            }
        }, 0, in);
    }

    @Nonnull
    public static Object builtin_mul(@CheckForNull Object in) {
        return fold(new Folder<Number>() {
            @Override
            public Number fold(Number left, Object right) {
                return left.doubleValue() * ((Number) right).doubleValue();
            }
        }, 1, in);
    }

    @Nonnull
    public static Object builtin_div(@CheckForNull Object in) {
        return fold(new Folder<Number>() {
            @Override
            public Number fold(Number left, Object right) {
                return left.doubleValue() / ((Number) right).doubleValue();
            }
        }, (Number) car(in), in);
    }

    @Nonnull
    private static LispCons newBuiltin(@Nonnull String name, @Nonnull String methodName) throws NoSuchMethodException {
        return cons(new LispIdentifier(name), LispBuiltins.class.getMethod(methodName, Object.class));
    }

    @Nonnull
    public static LispCons newBuiltins() throws NoSuchMethodException {
        return list(
                newBuiltin("+", "builtin_add"),
                newBuiltin("-", "builtin_sub"),
                newBuiltin("*", "builtin_mul"),
                newBuiltin("/", "builtin_div"),
                newBuiltin("car", "builtin_car"),
                newBuiltin("cdr", "builtin_cdr"),
                newBuiltin("cons", "builtin_cons"),
                newBuiltin("list", "builtin_list"));
    }
}
