package org.scriptkitty.perl.errorwarn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.scriptkitty.perl.errorwarn.CompilerErrorOrWarn;
import org.scriptkitty.perl.errorwarn.CompilerOutput;


public class TestCompilerOutputParser
{
    @Test public void testSingleLine()
    {
        String line = "Global symbol \"$foo\" requires explicit package name at test.pl line 3.";

        CompilerOutput output = CompilerOutput.parse(line);
        CompilerErrorOrWarn errorOrWarn = output.getErrorOrWarning();

        assertNotNull(output);

        assertEquals(3, output.getLineNumber());
        assertEquals("test.pl", output.getPath());
        assertEquals("Global symbol \"$foo\" requires explicit package name", output.getMessage());

        assertNotNull(errorOrWarn);
    }
}
