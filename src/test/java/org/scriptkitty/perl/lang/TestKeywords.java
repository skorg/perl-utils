package org.scriptkitty.perl.lang;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class TestKeywords
{
    //~ Methods

    @Test public void testNullKeyword()
    {
        Keyword unknown = Keyword.getKeyword("foo");
        
        assertNotNull(unknown);
        assertTrue(unknown.isNull());
        
        assertFalse(unknown.isBareword());
        assertFalse(unknown.isBuiltin());
    }
    
    @Test public void testBarewords()
    {
        Keyword keyword = Keyword.getKeyword("foreach");
        
        assertNotNull(keyword);
        assertTrue(keyword.isNull());
        
        assertTrue(keyword.isBareword());
        assertFalse(keyword.isBuiltin());
    }

    @Test public void testIsBuiltin()
    {
        Keyword keyword = Keyword.getKeyword("shift");
        assertNotNull(keyword);

        assertTrue(keyword.isBuiltin());
        assertTrue(keyword.isBuiltinWithOptionalArg());

        assertFalse(keyword.isBareword());
    }
}
