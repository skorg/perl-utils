package org.scriptkitty.perl.lang;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class TestLanguage
{
    //~ Methods

    @Test public void testQualifiedString()
    {
        assertTrue(Language.isQualified("Test::"));
        assertTrue(Language.isQualified("Test::Foo"));

        assertFalse(Language.isQualified("Test:"));
    }

    @Test public void testSigils()
    {
        assertTrue(Language.isArraySigil('@'));
        assertTrue(Language.isArraySigil("@"));
        assertFalse(Language.isArraySigil('$'));
        assertFalse(Language.isArraySigil("$"));

        assertTrue(Language.isHashSigil('%'));
        assertTrue(Language.isHashSigil("%"));

        assertFalse(Language.isHashSigil('$'));
        assertFalse(Language.isHashSigil("$"));

        assertTrue(Language.isScalarSigil('$'));
        assertTrue(Language.isScalarSigil("$"));

        assertFalse(Language.isScalarSigil('@'));
        assertFalse(Language.isScalarSigil("@"));
    }
}
