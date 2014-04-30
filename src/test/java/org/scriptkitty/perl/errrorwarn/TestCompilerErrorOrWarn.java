package org.scriptkitty.perl.errrorwarn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;
import org.scriptkitty.perl.errorwarn.CompilerErrorOrWarn;
import org.scriptkitty.perl.errorwarn.CompilerErrorOrWarn.Classification;
import org.scriptkitty.perl.errorwarn.CompilerErrorOrWarn.ClassificationType;


public class TestCompilerErrorOrWarn
{
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
