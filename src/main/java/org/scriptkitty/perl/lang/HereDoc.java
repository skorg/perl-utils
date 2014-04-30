package org.scriptkitty.perl.lang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HereDoc
{
    /** word pattern <code>/\s*`(.*)`$/</code> */
    private static final Pattern B_QUOTE = Pattern.compile("\\s*`(.*)`$");

    /** word pattern <code>/\(\w+)$/</code> */
    private static final Pattern B_SLASH = Pattern.compile("\\\\(\\w+)$");

    /** word pattern <code>/\s*\"(.*)\"$/</code> */
    private static final Pattern D_QUOTE = Pattern.compile("\\s*\"(.*)\"$");

    /** heredoc pattern <code>/^(\s* (?: "[^"]*" | '[^']*' | `[^`]*` | \\?\w+ ))/x</code> */
    private static final Pattern HEREDOC = Pattern.compile(
        "^(\\s* (?: \"[^\"]*\" | '[^']*' | \\`[^`]*` | \\\\?  \\w+))", Pattern.COMMENTS);

    /** word pattern <code>/\s*\'(.*)\'$/</code> */
    private static final Pattern S_QUOTE = Pattern.compile("\\s*\'(.*)\'$");

    /** word pattern <code>/^(\w+)$/</code> */
    private static final Pattern WORD = Pattern.compile("^(\\w+)$");

    public static String getIdentifier(String str)
    {
        String identifier = null;
        Matcher matcher = HEREDOC.matcher(str);

        if (matcher.find() && (parseTerminator(matcher.group(1)) != null))
        {
            identifier = matcher.group(1);
        }

        return identifier;
    }

    /**
     * does the passed string match a heredoc identifier?
     *
     * <p>this method can be used to determine if the content following <code>&lt;&lt;</code> comprises a herdoc identifier, ie: <code>
     * &lt;&lt;EOF</code> or <code>&lt;&lt;"EOF"</code>.</p>
     *
     * <p>NOTE: 'null' terminated heredoc is not yet supported.</p>
     *
     * @param  str string
     *
     * @return <code>true</code> if matches, <code>false</code> otherwise
     */
    public static boolean isIdentifier(String str)
    {
        return HEREDOC.matcher(str).find();
    }

    public static String parseTerminator(String content)
    {
        return parseTerminator(content, new TerminatorMode()
            {
                @Override public void setCommand()
                {
                    // no-op
                }

                @Override public void setInterpolate()
                {
                    // no-op
                }

                @Override public void setLiteral()
                {
                    // no-op
                }
            });
    }

    public static String parseTerminator(String content, TerminatorMode mode)
    {
        Matcher matcher = WORD.matcher(content);

        if (matcher.find())
        {
            mode.setInterpolate();
            return matcher.group(1);
        }

        if (matcher.usePattern(S_QUOTE).find())
        {
            mode.setLiteral();
            return matcher.group(1);
        }

        if (matcher.usePattern(D_QUOTE).find())
        {
            mode.setInterpolate();
            return matcher.group(1);
        }

        if (matcher.usePattern(B_QUOTE).find())
        {
            mode.setCommand();
            return matcher.group(1);
        }

        if (matcher.usePattern(B_SLASH).find())
        {
            mode.setLiteral();
            return matcher.group(1);
        }

        return null;
    }

    public interface TerminatorMode
    {
        void setCommand();

        void setInterpolate();

        void setLiteral();
    }
}
