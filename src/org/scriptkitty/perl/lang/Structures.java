package org.scriptkitty.perl.lang;

public class Structures
{
    //~ Methods

    /**
     * does the string represent a closing brace?
     *
     * @param  s string
     *
     * @return <code>true</code> if closing brace, <code>false</code> otherwise
     *
     * @see    #isOpenCurly(String)
     * @see    #isOpenParen(String)
     * @see    #isOpenSquare(String)
     */
    public static boolean isCloseBrace(String s)
    {
        return (isCloseSquare(s) || isCloseCurly(s) || isCloseParen(s));
    }

    /**
     * does the string represent a closing curly <code>}</code>?
     *
     * @param  s string
     *
     * @return <code>true</code> if closing curly, <code>false</code> otherwise
     */
    public static boolean isCloseCurly(String s)
    {
        return "}".equals(s);
    }

    /**
     * does the string represent a closing paren <code>)</code>?
     *
     * @param  s string
     *
     * @return <code>true</code> if closing paren, <code>false</code> otherwise
     */
    public static boolean isCloseParen(String s)
    {
        return ")".equals(s);
    }

    /**
     * does the string represent a closing square <code>]</code>?
     *
     * @param  s string
     *
     * @return <code>true</code> if closing square, <code>false</code> otherwise
     */
    public static boolean isCloseSquare(String s)
    {
        return "]".equals(s);
    }

    /**
     * does the string represent an opening brace?
     *
     * @param  s string
     *
     * @return <code>true</code> if opening brace, <code>false</code> otherwise
     *
     * @see    #isOpenCurly(String)
     * @see    #isOpenParen(String)
     * @see    #isOpenSquare(String)
     */
    public static boolean isOpenBrace(String s)
    {
        return (isOpenSquare(s) || isOpenCurly(s) || isOpenParen(s));
    }

    /**
     * does the string represent a open curly <code>{</code>?
     *
     * @param  s string
     *
     * @return <code>true</code> if open square, <code>false</code> otherwise
     */
    public static boolean isOpenCurly(String s)
    {
        return "{".equals(s);
    }

    /**
     * does the string represent a open paren <code>(</code>?
     *
     * @param  s string
     *
     * @return <code>true</code> if open paren, <code>false</code> otherwise
     */
    public static boolean isOpenParen(String s)
    {
        return "(".equals(s);
    }

    /**
     * does the string represent a open square <code>[</code>?
     *
     * @param  s string
     *
     * @return <code>true</code> if open square, <code>false</code> otherwise
     */
    public static boolean isOpenSquare(String s)
    {
        return "[".equals(s);
    }

    /**
     * does the string represent a semi-colon <code>;</code>?
     *
     * @param  s string
     *
     * @return <code>true</code> if semi-colon, <code>false</code> otherwise
     */
    public static boolean isSemiColon(String s)
    {
        return ";".equals(s);
    }
}
