package org.scriptkitty.perl.lang;

import java.util.ResourceBundle;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

import org.scriptkitty.perl.internal.AbstractKeyOrSym;
import org.scriptkitty.perl.internal.ResourceBundleFactory;


@XmlRootElement public class Symbol extends AbstractKeyOrSym
{
    //~ Static fields/initializers

    public static final String symbols = "symbols";

    private static ResourceBundle bundle = ResourceBundleFactory.getBundle(symbols);

    private static final Symbol NULL = new Symbol();

    //~ Enums

    @XmlEnum private enum Type
    {
        @XmlEnumValue("A")
        A,
        @XmlEnumValue("F")
        F,
        @XmlEnumValue("H")
        H, NULL,
        @XmlEnumValue("S")
        S
    }

    //~ Instance fields

    @XmlElement private Type type;

    //~ Constructors

    private Symbol()
    {
        super();
        this.type = Type.NULL;
    }

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
        return AbstractKeyOrSym.get(symbol, bundle, NULL);
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
        return (type == Type.A);
    }

    /**
     * is this a file handle builtin?
     *
     * @return <code>true</code> if file handle builtin, <code>false</code> otherwise
     */
    public boolean isFileHandle()
    {
        return (type == Type.F);
    }

    /**
     * is this a hash builtin?
     *
     * @return <code>true</code> if hash builtin, <code>false</code> otherwise
     */
    public boolean isHashBuiltin()
    {
        return (type == Type.H);
    }

    /**
     * is this the '<code>null</code>' object
     *
     * @return <code>true</code> if hash builtin, <code>false</code> otherwise
     */
    @Override public boolean isNull()
    {
        return (type == Type.NULL);
    }

    /**
     * is this a scalar builtin?
     *
     * @return <code>true</code> if scalar builtin, <code>false</code> otherwise
     */
    public boolean isScalarBuiltin()
    {
        return (type == Type.S);
    }
}
