package org.scriptkitty.perl.compiler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.scriptkitty.perl.internal.AbstractErrorOrWarn;


public class TestCompilerOutputParser
{
    //~ Methods

    @Test public void testSingleLine()
    {
        String line = "Global symbol \"$foo\" requires explicit package name at test.pl line 3.";

        CompilerOutput output = CompilerOutput.parse(line);
        AbstractErrorOrWarn errorOrWarn = output.getErrorOrWarning();

        assertNotNull(output);

        assertEquals(3, output.getLineNumber());
        assertEquals("test.pl", output.getPath());
        assertEquals("Global symbol \"$foo\" requires explicit package name", output.getMessage());

        assertNotNull(errorOrWarn);        
    }
}
