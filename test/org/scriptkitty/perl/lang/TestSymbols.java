package org.scriptkitty.perl.lang;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestSymbols
{
    @Test public void testArrayBuiltin()
    {
        assertTrue(Symbol.isBuiltinSymbol("@ARGV"));
        assertFalse(Symbol.isBuiltinSymbol("@foo"));
        
        Symbol symbol = Symbol.getSymbol("@ARGV");
        assertNotNull(symbol);
        
        assertTrue(symbol.isArrayBuiltin());
    
        assertNull(Symbol.getSymbol("@foo"));
    }
    
    @Test public void testIsHash()
    {
        assertTrue(Symbol.isBuiltinSymbol("%ENV"));
        assertFalse(Symbol.isBuiltinSymbol("@foo"));
        
        Symbol symbol = Symbol.getSymbol("%ENV");
        assertNotNull(symbol);
        
        assertTrue(symbol.isHashBuiltin());
    
        assertNull(Symbol.getSymbol("@foo"));
    }
    
    @Test public void testIsScalar()
    {
        assertTrue(Symbol.isBuiltinSymbol("$a"));
        assertFalse(Symbol.isBuiltinSymbol("$foo"));
        
        Symbol symbol = Symbol.getSymbol("$a");
        assertNotNull(symbol);
        
        assertTrue(symbol.isScalarBuiltin());
    
        assertNull(Symbol.getSymbol("$foo"));
    }
}
