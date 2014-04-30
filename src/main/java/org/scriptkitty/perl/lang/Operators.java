package org.scriptkitty.perl.lang;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.scriptkitty.perl.internal.Constants;

public class Operators
{
    public static Collection<String> getQuoteAndRegExpOperators()
    {
      return new HashSet<>(Arrays.asList(Constants.QUOTE_AND_REGEXP));
    }
    
    public static boolean isDashArrow(String op)
    {
        return "->".equals(op);
    }

    public static boolean isEqualArrow(String op)
    {
        return "=>".equals(op);
    }       
}
