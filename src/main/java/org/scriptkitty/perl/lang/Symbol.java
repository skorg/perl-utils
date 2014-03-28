package org.scriptkitty.perl.lang;

import org.scriptkitty.perl.internal.ResourceBundleFactory;

import java.util.ResourceBundle;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement public class Symbol
{
    //~ Static fields/initializers

    public static final String symbols = "symbols";

    private static ResourceBundle bundle = ResourceBundleFactory.getBundle(symbols);

    //~ Enums

    @XmlEnum private enum SymbolType
    {
        @XmlEnumValue("A")
        A,
        @XmlEnumValue("F")
        F,
        @XmlEnumValue("H")
        H,
        @XmlEnumValue("S")
        S
    }

    //~ Instance fields

    @XmlElement private String content;

    @XmlElement private String keyword;

    @XmlElement private SymbolType type;

    //~ Methods

    /**
     * get the <code>Symbol</code> object that represents the specified perl builtin
     *
     * @param  symbol builtin
     *
     * @return <code>Symbol</code> object or <code>null</code> if the specified symbol is not a builtin
     */
    public static Symbol getSymbol(String symbol)
    {
        if (!bundle.containsKey(symbol))
        {
            return null;
        }
        
        return (Symbol) bundle.getObject(symbol);
    }

    /**
     * does the string represent a builtin perl symbol?
     *
     * @param  s string
     *
     * @return <code>true</code> if builtin symbol, <code>false</code> otherwise
     */
    public static boolean isBuiltinSymbol(String s)
    {
        return bundle.containsKey(s);
    }

    /**
     * is this a array builtin?
     *
     * @return <code>true</code> if array builtin, <code>false</code> otherwise
     */
    public boolean isArrayBuiltin()
    {
        return (type == SymbolType.A);
    }

    /**
     * is this a file handle builtin?
     *
     * @return <code>true</code> if file handle builtin, <code>false</code> otherwise
     */
    public boolean isFileHandle()
    {
        return (type == SymbolType.F);
    }

    /**
     * is this a hash builtin?
     *
     * @return <code>true</code> if hash builtin, <code>false</code> otherwise
     */
    public boolean isHashBuiltin()
    {
        return (type == SymbolType.H);
    }

    /**
     * is this a scalar builtin?
     *
     * @return <code>true</code> if scalar builtin, <code>false</code> otherwise
     */
    public boolean isScalarBuiltin()
    {
        return (type == SymbolType.S);
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override public String toString()
    {
        return keyword;
    }
}
