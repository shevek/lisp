/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.lisp;

import javax.annotation.Nonnull;

/**
 *
 * @author shevek
 */
public class LispIdentifier {

    private final String name;

    public LispIdentifier(@Nonnull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (null == obj)
            return false;
        if (!getClass().equals(obj.getClass()))
            return false;
        LispIdentifier o = (LispIdentifier) obj;
        return name.equals(o.name);
    }

    @Override
    public String toString() {
        return "#" + getName();
    }
}
