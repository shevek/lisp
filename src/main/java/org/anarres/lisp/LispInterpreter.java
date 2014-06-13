/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.lisp;

import com.google.common.base.Throwables;
import java.lang.reflect.Method;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static org.anarres.lisp.LispRuntime.*;

/**
 *
 * @author shevek
 */
public class LispInterpreter {

    private static final Log LOG = LogFactory.getLog(LispInterpreter.class);

    @CheckForNull
    public Object apply(LispCons env, Object function, Object args) {
        try {
            /*
             List<Object> jargs = new ArrayList<Object>();
             while (args != null) {
             jargs.add(car(args));
             args = cdr(args);
             }
             jargs.toArray(new Object[jargs.size()]));
             */
            Method method = (Method) function;
            // LOG.info("Apply " + method + " to " + args);
            Object result = method.invoke(null, args);
            // LOG.info("Return " + result);
            return result;
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @CheckForNull
    public LispCons evalargs(LispCons env, @CheckForNull Object args) {
        // LOG.info("Evalargs " + args);
        if (args == null)
            return null;
        return cons(eval(env, car(args)), evalargs(env, cdr(args)));
    }

    @CheckForNull
    public Object eval(LispCons env, Object value) {
        // LOG.info("Eval " + value);
        if (consp(value)) {
            Object function = eval(env, car(value));
            return apply(env, function, evalargs(env, cdr(value)));
        } else if (symbolp(value)) {
            while (env != null) {
                LispCons pair = (LispCons) car(env);
                if (value.equals(car(pair)))
                    return cdr(pair);
                env = (LispCons) cdr(env);
            }
            throw new IllegalArgumentException("Unbound symbol " + value);
        } else {
            return value;
        }
    }
}
