package org.scriptkitty.perl.lang;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class TestSymbols
{
    @Test public void testArrayBuiltin()
    {
        Symbol symbol = Symbol.getSymbol("@ARGV");

        assertFalse(symbol.isNull());
        assertFalse(symbol.isFileHandleBuiltin());
        assertFalse(symbol.isHashBuiltin());
        assertFalse(symbol.isScalarBuiltin());

        assertTrue(symbol.isArrayBuiltin());
        assertTrue(Symbol.getSymbol("@foo").isNull());
    }

    @Test public void testIsBuiltin()
    {
        assertTrue(Symbol.isBuiltinSymbol("$a"));
        assertTrue(Symbol.isBuiltinSymbol("@ARG"));
        assertTrue(Symbol.isBuiltinSymbol("%ENV"));

        assertFalse(Symbol.isBuiltinSymbol("$foo"));
        assertFalse(Symbol.isBuiltinSymbol("@foo"));
        assertFalse(Symbol.isBuiltinSymbol("%hash"));
    }

    @Test public void testIsHash()
    {
        Symbol symbol = Symbol.getSymbol("%ENV");

        assertFalse(symbol.isNull());
        assertFalse(symbol.isFileHandleBuiltin());
        assertFalse(symbol.isArrayBuiltin());
        assertFalse(symbol.isScalarBuiltin());

        assertTrue(symbol.isHashBuiltin());
        assertTrue(Symbol.getSymbol("%foo").isNull());
    }

    @Test public void testIsScalar()
    {
        Symbol symbol = Symbol.getSymbol("$a");

        assertFalse(symbol.isNull());
        assertFalse(symbol.isFileHandleBuiltin());
        assertFalse(symbol.isHashBuiltin());
        assertFalse(symbol.isArrayBuiltin());

        assertTrue(symbol.isScalarBuiltin());
        assertTrue(Symbol.getSymbol("$foo").isNull());
    }
}
