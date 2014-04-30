package org.scriptkitty.perl.lang;

import java.util.Collection;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

import org.scriptkitty.perl.internal.Constants;
import org.scriptkitty.perl.internal.ResourceBundleFactory;


@XmlRootElement public class Keyword extends AbstractKeyOrSym
{
    public static final String keywords = "keywords";

    private static ResourceBundle bundle = ResourceBundleFactory.getBundle(keywords);

    private static final Keyword NULL = new Keyword();

    /** pragma pattern - <code>/^[a-z][a-z\\d]*$/</code> */
    private static final Pattern PRAGMA = Pattern.compile("^[a-z][a-z\\d]*$");

    @XmlEnum private enum Type
    {
        /**
         * builtin that accepts a list
         */
        @XmlEnumValue("L")
        L,
        /**
         * builtin that accepts multiple arguments
         */
        @XmlEnumValue("M")
        M,
        /**
         * builtin that accepts no arguments
         */
        @XmlEnumValue("N")
        N,
        /**
         * represents <code>null</code>
         */
        @XmlEnumValue("NULL")
        NULL,
        /**
         * builtin that accepts optional arguments
         */
        @XmlEnumValue("O")
        O,
        /**
         * builtin that accepts one argument
         */
        @XmlEnumValue("R")
        R
    }

    @XmlElement private boolean bareword;

    @XmlElement private Type type;

    private Keyword()
    {
        super();
        this.type = Type.NULL;
    }

    public static Collection<Keyword> getBarewords()
    {
        return AbstractKeyOrSym.find(bundle, new IFindCallback<Keyword>()
            {
                @Override public boolean isWanted(Keyword object)
                {
                    return object.isBareword();
                }
            });
    }

    public static Collection<Keyword> getFunctions()
    {
        return AbstractKeyOrSym.find(bundle, new IFindCallback<Keyword>()
            {
                @Override public boolean isWanted(Keyword object)
                {
                    return !object.isBareword();
                }
            });
    }

    /**
     * get the <code>Keyword</code> object that represents the specified perl builtin
     *
     * @param  keyword builtin
     *
     * @return <code>Keyword</code> object or <code>null</code> if the specified keyword is not a builtin
     */
    public static Keyword getKeyword(String keyword)
    {
        return AbstractKeyOrSym.get(keyword, bundle, NULL);
    }

    /**
     * does the string represent a builtin perl symbol?
     *
     * @param  s string
     *
     * @return <code>true</code> if builtin symbol, <code>false</code> otherwise
     */
    public static boolean isBuiltinKeyword(String s)
    {
        return bundle.containsKey(s);
    }

    public static boolean isPragma(String s)
    {
        return PRAGMA.matcher(s).find();
    }

    public boolean isBareword()
    {
        return bareword;
    }

    public boolean isBaseKeyword()
    {
        return Constants.BASE.equals(keyword);
    }

    public boolean isBuiltin()
    {
        return (type != Type.NULL);
    }

    public boolean isBuiltinWithListContext()
    {
        return (type == Type.L);
    }

    public boolean isBuiltinWithMultipleArgs()
    {
        return (type == Type.M);
    }

    public boolean isBuiltinWithNoArgs()
    {
        return (type == Type.N);
    }

    public boolean isBuiltinWithOneArg()
    {
        return (type == Type.R);
    }

    public boolean isBuiltinWithOptionalArg()
    {
        return (type == Type.O);
    }

    public boolean isBuiltinWithZeroOrOneArgs(String word)
    {
        return (isBuiltinWithNoArgs() || isBuiltinWithOneArg() || isBuiltinWithOptionalArg());
    }

    public boolean isCompoundKeyword()
    {
        return isOneOf(keyword, Constants.COMPOUND);
    }

    public boolean isConditionalKeyword()
    {
        return isOneOf(keyword, Constants.CONDITIONAL);
    }

    public boolean isContinueKeyword()
    {
        return Constants.CONTINUE.equals(keyword);
    }

    public boolean isControlKeyword()
    {
        return isOneOf(keyword, Constants.CONTROL);
    }

    public boolean isDataSeparator()
    {
        return Constants.DATA_SEP.equals(keyword);
    }

    public boolean isElseKeyword()
    {
        return Constants.ELSE.equals(keyword);
    }

    public boolean isElsIfKeyword()
    {
        return Constants.ELSIF.equals(keyword);
    }

    public boolean isEndSeparator()
    {
        return Constants.END_SEP.equals(keyword);
    }

    public boolean isFieldsKeyword()
    {
        return Constants.FIELDS.equals(keyword);
    }

    public boolean isForeachKeyword()
    {
        return Constants.FOREACH.equals(keyword);
    }

    public boolean isForKeyword()
    {
        return Constants.FOR.equals(keyword);
    }

    public boolean isIfKeyword()
    {
        return Constants.IF.equals(keyword);
    }

    public boolean isLoopKeyword()
    {
        return isOneOf(keyword, Constants.LOOP);
    }

    public boolean isNoKeyword()
    {
        return Constants.NO.equals(keyword);
    }

    @Override public boolean isNull()
    {
        return (type == Type.NULL);
    }

    public boolean isPackageKeyword()
    {
        return Constants.PACKAGE.equals(keyword);
    }

    public boolean isParentKeyword()
    {
        return Constants.PARENT.equals(keyword);
    }

    public boolean isReturnKeyword()
    {
        return Constants.RETURN.equals(keyword);
    }

    public boolean isScheduledKeyword()
    {
        return isOneOf(keyword, Constants.SCHEDULED);
    }

    public boolean isStrictKeyword()
    {
        return Constants.STRICT.equals(keyword);
    }

    public boolean isSubKeyword()
    {
        return Constants.SUB.equals(keyword);
    }

    public boolean isUnlessKeyword()
    {
        return Constants.UNLESS.equals(keyword);
    }

    public boolean isUntilKeyword()
    {
        return Constants.UNTIL.equals(keyword);
    }

    public boolean isUseKeyword()
    {
        return Constants.USE.equals(keyword);
    }

    public boolean isV6Keyword()
    {
        return Constants.V6.equals(keyword);
    }

    public boolean isVariableKeyword()
    {
        return isOneOf(keyword, Constants.VARIABLE);
    }

    public boolean isWhileKeyword()
    {
        return Constants.WHILE.equals(keyword);
    }

    private boolean isOneOf(String s, String[] choices)
    {
        for (String type : choices)
        {
            if (type.equals(s))
            {
                return true;
            }
        }

        return false;
    }
}
