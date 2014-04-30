package org.scriptkitty.perl.lang;

import org.scriptkitty.perl.internal.Constants;


public class Language
{
    /**
     * does the specified character represent the ampersand sigil (<code>&</code>)?
     *
     * @param  c char
     *
     * @return <code>true</code> if the ampersand sigil, <code>false</code> otherwise
     */
    public static boolean isAmpSigil(char c)
    {
        return (c == Constants.AMP_C);
    }

    /**
     * does the specified string represent the ampersand sigil (<code>&</code>)?
     *
     * @param  s string
     *
     * @return <code>true</code> if the ampersand sigil, <code>false</code> otherwise
     */
    public static boolean isAmpSigil(String s)
    {
        return Constants.AMP_S.equals(s);
    }

    /**
     * does the specified character represent the array sigil (<code>@</code>)?
     *
     * @param  c char
     *
     * @return <code>true</code> if the array sigil, <code>false</code> otherwise
     */
    public static boolean isArraySigil(char c)
    {
        return (c == Constants.ARRAY_C);
    }

    /**
     * does the specified string represent the array sigil (<code>@</code>)?
     *
     * @param  s string
     *
     * @return <code>true</code> if the array sigil, <code>false</code> otherwise
     */
    public static boolean isArraySigil(String s)
    {
        return Constants.ARRAY_S.equals(s);
    }

    /**
     * does the specified string represent the 'dollar pound' (<code>$#</code>)?
     *
     * @param  s string
     *
     * @return <code>true</code> if the 'dollar pound', <code>false</code> otherwise
     */
    public static boolean isDollarPound(String s)
    {
        return Constants.DOLLAR_POUND.equals(s);
    }

    /**
     * does the specified character represent the hash sigil (<code>%</code>)?
     *
     * @param  c char
     *
     * @return <code>true</code> if the hash sigil, <code>false</code> otherwise
     */
    public static boolean isHashSigil(char c)
    {
        return (c == Constants.HASH_C);
    }

    /**
     * does the specified string represent the hash sigil (<code>%</code>)?
     *
     * @param  s string
     *
     * @return <code>true</code> if the hash sigil, <code>false</code> otherwise
     */
    public static boolean isHashSigil(String s)
    {
        return Constants.HASH_S.equals(s);
    }

    /**
     * does the specified string have a module component, ie: contains <code>::</code>
     *
     * @param  str string
     *
     * @return <code>true</code> if the string has a module component, <code>false</code> otherwise
     */
    public static boolean isQualified(String str)
    {
        return str.contains(Constants.DOUBLE_COLON);
    }

    /**
     * does the specified character represent the scalar sigil (<code>$</code>)?
     *
     * @param  c char
     *
     * @return <code>true</code> if the scalar sigil, <code>false</code> otherwise
     */
    public static boolean isScalarSigil(char c)
    {
        return (c == Constants.SCALAR_C);
    }

    /**
     * does the specified string represent the scalar sigil (<code>$</code>)?
     *
     * @param  s string
     *
     * @return <code>true</code> if the scalar sigil, <code>false</code> otherwise
     */
    public static boolean isScalarSigil(String s)
    {
        return Constants.SCALAR_S.equals(s);
    }
}
