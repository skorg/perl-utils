package org.scriptkitty.perl.lang;

import java.util.ResourceBundle;


public class Symbols
{
    //~ Static fields/initializers

    private static ResourceBundle arrayKeywords = ResourceBundle.getBundle("resources.arrayKeywords");

    private static ResourceBundle hashKeywords = ResourceBundle.getBundle("resources.hashKeywords");

    private static ResourceBundle scalarKeywords = ResourceBundle.getBundle("resources.scalarKeywords");

    //~ Methods

    public static boolean isAmpSigil(char c)
    {
        return (c == '&');
    }

    public static boolean isAmpSigil(String s)
    {
        return "&".equals(s);
    }

    public static boolean isArrayBuiltin(String symbol)
    {
        return arrayKeywords.containsKey(symbol);
    }

    public static boolean isArraySigil(char c)
    {
        return (c == '@');
    }

    public static boolean isArraySigil(String s)
    {
        return "@".equals(s);
    }

    public static boolean isBuiltin(String symbol)
    {
        // TODO: file handles...
        return (isArrayBuiltin(symbol) || isHashBuiltin(symbol) || isScalarBuiltin(symbol));
    }

    public static boolean isDollarPound(String s)
    {
        return "$#".equals(s);
    }

    public static boolean isHashBuiltin(String symbol)
    {
        return hashKeywords.containsKey(symbol);
    }

    public static boolean isHashSigil(char c)
    {
        return (c == '%');
    }

    public static boolean isHashSigil(String s)
    {
        return "%".equals(s);
    }

    public static boolean isScalarBuiltin(String symbol)
    {
        return scalarKeywords.containsKey(symbol);
    }

    public static boolean isScalarSigil(char c)
    {
        return (c == '$');
    }

    public static boolean isScalarSigil(String s)
    {
        return "$".equals(s);
    }
}
