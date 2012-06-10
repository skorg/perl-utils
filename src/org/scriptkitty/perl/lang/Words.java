package org.scriptkitty.perl.lang;

import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class Words
{
    //~ Static fields/initializers

    /** pragma pattern - <code>/^[a-z][a-z\\d]*$/</code> */
    private static final Pattern PRAGMA = Pattern.compile("^[a-z][a-z\\d]*$");

    private static String[] COMPOUND = { "if", "unless", "for", "foreach", "while", "until" };

    private static String[] CONDITIONAL = { "if", "elsif", "unless", "until", "while" };

    private static String[] CONTROL = { "redo", "next", "last", "return", "goto" };

    private static String[] LOOP = { "while", "until", "for", "foreach" };

    private static String[] SCHEDULED = { "BEGIN", "CHECK", "INIT", "END", "UNITCHECK" };

    private static String[] VARIABLE = { "my", "local", "our", "state" };

    private static ResourceBundle barewordKeywords = ResourceBundle.getBundle("resources.barewordKeywords");

    private static ResourceBundle builtinListContext = ResourceBundle.getBundle("resources.builtinListContext");

    private static ResourceBundle builtinMultiArgs = ResourceBundle.getBundle("resources.builtinMultiArgs");

    private static ResourceBundle builtinNoArgs = ResourceBundle.getBundle("resources.builtinNoArgs");

    private static ResourceBundle builtinOneArg = ResourceBundle.getBundle("resources.builtinOneArg");

    private static ResourceBundle builtinOptArgs = ResourceBundle.getBundle("resources.builtinOptArgs");

    private static ResourceBundle fileHandleKeywords = ResourceBundle.getBundle("resources.fileHandleKeywords");

    private static ResourceBundle functionKeywords = ResourceBundle.getBundle("resources.functionKeywords");

    //~ Methods

    public static String getPackageKeyword()
    {
        return "package";
    }

    public static boolean isBareword(String word)
    {
        return barewordKeywords.containsKey(word);
    }

    public static boolean isBaseKeyword(String s)
    {
        return "base".equals(s);
    }

    public static boolean isBuiltin(String word)
    {
        return functionKeywords.containsKey(word);
    }

    public static boolean isBuiltinWithListContext(String word)
    {
        return builtinListContext.containsKey(word);
    }

    public static boolean isBuiltinWithMultipleArgs(String word)
    {
        return builtinMultiArgs.containsKey(word);
    }

    public static boolean isBuiltinWithNoArgs(String word)
    {
        return builtinNoArgs.containsKey(word);
    }

    public static boolean isBuiltinWithOneArg(String word)
    {
        return builtinOneArg.containsKey(word);
    }

    public static boolean isBuiltinWithOptionalArg(String word)
    {
        return builtinOptArgs.containsKey(word);
    }

    public static boolean isBuiltinWithZeroOrOneArgs(String word)
    {
        return (isBuiltinWithNoArgs(word) || isBuiltinWithOneArg(word) || isBuiltinWithOptionalArg(word));
    }

    public static boolean isCompoundKeyword(String word)
    {
        return isOneOf(word, COMPOUND);
    }

    public static boolean isConditionalKeyword(String word)
    {
        return isOneOf(word, CONDITIONAL);
    }

    public static boolean isContinueKeyword(String word)
    {
        return "continue".equals(word);
    }

    public static boolean isControlKeyword(String word)
    {
        return isOneOf(word, CONTROL);
    }

    public static boolean isDataSeparator(String word)
    {
        return "__DATA__".equals(word);
    }

    public static boolean isElseKeyword(String s)
    {
        return "else".equals(s);
    }

    public static boolean isElsIfKeyword(String s)
    {
        return "elsif".equals(s);
    }

    public static boolean isEndSeparator(String word)
    {
        return "__END__".equals(word);
    }

    public static boolean isFieldsKeyword(String s)
    {
        return "fields".equals(s);
    }

    public static boolean isFileHandle(String word)
    {
        return fileHandleKeywords.containsKey(word);
    }

    public static boolean isForeachKeyword(String s)
    {
        return "foreach".equals(s);
    }

    public static boolean isForKeyword(String s)
    {
        return "for".equals(s);
    }

    public static boolean isIfKeyword(String s)
    {
        return "if".equals(s);
    }

    public static boolean isLoopKeyword(String word)
    {
        return isOneOf(word, LOOP);
    }

    public static boolean isNoKeyword(String s)
    {
        return "no".equals(s);
    }

    public static boolean isPackageKeyword(String word)
    {
        return getPackageKeyword().equals(word);
    }

    public static boolean isParentKeyword(String s)
    {
        return "parent".equals(s);
    }

    public static boolean isPragma(String s)
    {
        return PRAGMA.matcher(s).find();
    }

    public static boolean isScheduledKeyword(String word)
    {
        return isOneOf(word, SCHEDULED);
    }

    public static boolean isStrictKeyword(String s)
    {
        return "strict".equals(s);
    }

    public static boolean isSubKeyword(String word)
    {
        return "sub".equals(word);
    }

    public static boolean isUnlessKeyword(String s)
    {
        return "unless".equals(s);
    }

    public static boolean isUntilKeyword(String s)
    {
        return "until".equals(s);
    }

    public static boolean isUseKeyword(String s)
    {
        return "use".equals(s);
    }

    public static boolean isV6Keyword(String s)
    {
        return "v6".equals(s);
    }

    public static boolean isVariableKeyword(String word)
    {
        return isOneOf(word, VARIABLE);
    }

    public static boolean isWhileKeyword(String s)
    {
        return "while".equals(s);
    }

    private static boolean isOneOf(String s, String[] choices)
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
