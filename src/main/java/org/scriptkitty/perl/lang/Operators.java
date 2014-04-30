package org.scriptkitty.perl.lang;

public class Operators
{
    public static boolean isDashArrow(String op)
    {
        return "->".equals(op);
    }

    public static boolean isEqualArrow(String op)
    {
        return "=>".equals(op);
    }
}
