/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.lisp;

/**
 * A cons cell.
 * 
 * The values of the registers may be any legal type, including (wrapped) primitives and conses.
 *
 * @author shevek
 */
public class LispCons {

    public Object car;
    public Object cdr;

    public LispCons(Object car, Object cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    public LispCons() {
    }

    public void toString(StringBuilder buf) {
        buf.append(car);
        if (cdr == null)
            return;
        buf.append(' ');
        if (!LispRuntime.consp(cdr))
            buf.append(". ").append(cdr);
        else
            ((LispCons) cdr).toString(buf);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("(");
        toString(buf);
        buf.append(")");
        return buf.toString();
    }
}
