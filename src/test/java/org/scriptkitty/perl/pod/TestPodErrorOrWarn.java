package org.scriptkitty.perl.pod;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.scriptkitty.perl.errorwarn.PodErrorOrWarn;
import org.scriptkitty.perl.errorwarn.PodErrorOrWarn.ClassificationType;


public class TestPodErrorOrWarn
{
    @Test public void testPodError()
    {
        String line = "=back without previous =over at line 2 in file test.pl";
        PodErrorOrWarn errorOrWarn = PodErrorOrWarn.getErrorOrWarning(line);

        assertNotNull(errorOrWarn);
        assertTrue(errorOrWarn.matches(line));

        assertEquals(ClassificationType.E, errorOrWarn.getClassification());
    }
}
