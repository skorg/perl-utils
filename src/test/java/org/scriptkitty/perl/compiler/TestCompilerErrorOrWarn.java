package org.scriptkitty.perl.compiler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import org.scriptkitty.perl.compiler.CompilerErrorOrWarn.Classification;
import org.scriptkitty.perl.compiler.CompilerErrorOrWarn.ClassificationType;


public class TestCompilerErrorOrWarn
{
    //~ Methods

    @Test public void testCompilerError()
    {
        String line = "Global symbol \"$foo\" requires explicit package name at test.pl line 3.";
        CompilerErrorOrWarn errorOrWarn = CompilerErrorOrWarn.getErrorOrWarning(line);

        assertNotNull(errorOrWarn);
        assertTrue(errorOrWarn.matches(line));

        Collection<Classification> classifications = errorOrWarn.getClassifications();
        assertEquals(1, classifications.size());

        assertEquals(ClassificationType.F, errorOrWarn.getClassifications().iterator().next().getType());
    }
}
