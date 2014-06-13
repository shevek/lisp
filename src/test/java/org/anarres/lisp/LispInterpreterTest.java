/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.lisp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author shevek
 */
public class LispInterpreterTest {

    private static final Log LOG = LogFactory.getLog(LispInterpreterTest.class);

    private void test(String in, Object out) throws Exception {
        LispParser parser = new LispParser(in);
        Object expression = parser.readExpression();
        LOG.info("Parsed " + expression);

        LispCons environment = LispBuiltins.newBuiltins();
        // LOG.info("Environment " + environment);

        LispInterpreter interpreter = new LispInterpreter();
        Object result = interpreter.eval(environment, expression);
        LOG.info("Evaluated " + result);

        assertEquals(out, result);
    }

    @Test
    public void testInterpreter() throws Exception {
        test("(+ (* 3 4) 5 6)", 23d);
        test("(+ 3 4 5 6)", 18d);
        test("(car (list 3 4 5 6))", 3L);
    }
}