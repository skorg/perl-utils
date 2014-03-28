package org.scriptkitty.perl.lang;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class TestKeywords
{
    //~ Methods

    @Test public void testBarewords()
    {
        assertTrue(Keyword.isBuiltinKeyword("foreach"));
        assertFalse(Keyword.isBuiltinKeyword("foo"));

        Keyword keyword = Keyword.getKeyword("foreach");
        assertNotNull(keyword);

        assertTrue(keyword.isBareword());
        assertFalse(keyword.isBuiltin());

        assertNull(Keyword.getKeyword("foo"));
    }

    @Test public void testIsBuiltin()
    {
        assertTrue(Keyword.isBuiltinKeyword("shift"));
        assertFalse(Keyword.isBuiltinKeyword("foo"));

        Keyword keyword = Keyword.getKeyword("shift");
        assertNotNull(keyword);

        assertTrue(keyword.isBuiltin());
        assertTrue(keyword.isBuiltinWithOptionalArg());

        assertFalse(keyword.isBareword());

        assertNull(Keyword.getKeyword("foo"));
    }
}
