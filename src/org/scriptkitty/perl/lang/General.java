package org.scriptkitty.perl.lang;

public class General
{
    /**
     * does the given string have a module component, ie: contains <code>::</code>
     * 
     * @param str string
     * 
     * @return <code>true</code> if the string has a module component, <code>false</code> otherwise
     */
    public static boolean isQualified(String str)
    {
        return str.contains("::");
    }
}
