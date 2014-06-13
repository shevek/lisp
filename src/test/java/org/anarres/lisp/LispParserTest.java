/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.lisp;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 *
 * @author shevek
 */
public class LispParserTest {

    private static final Log LOG = LogFactory.getLog(LispParserTest.class);

    private void test(String in) throws IOException {
        LOG.info("Input: " + in);
        LispParser parser = new LispParser(in);
        Object value = parser.readExpression();
        LOG.info("Output: " + value);
    }

    @Test
    public void testParser() throws IOException {
        test("5");
        test("()");
        test("(5)");
        test("(1 2)");
        test("(1 (2 3))");
        test("((1 2) 3)");
        test("((1 2) (3 4))");
    }
}