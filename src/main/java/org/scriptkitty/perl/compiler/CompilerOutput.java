package org.scriptkitty.perl.compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class CompilerOutput
{
    private static final Pattern compiler = Pattern.compile("^(.*) at (\\S+) line (\\d+)[\\.,]?");

    private static final Logger logger = LoggerFactory.getLogger(CompilerOutput.class);

    private final int lineNo;

    private final String message;

    private final String path;

    CompilerOutput(String message, String path, int lineNo)
    {
        this.message = message;
        this.path = path;
        this.lineNo = lineNo;
    }

    public static List<CompilerOutput> parse(List<String> lines)
    {
        List<CompilerOutput> list = new ArrayList<>();

        for (String line : lines)
        {
            CompilerOutput output = parse(line);
            if (output != null)
            {
                list.add(output);
            }
        }

        return list;
    }

    public static CompilerOutput parse(String line)
    {
        Matcher matcher = compiler.matcher(line);
        if (!matcher.find())
        {
            logger.error("unable to parse line [{}] against pattern [{}]", line, compiler.pattern());
            return null;
        }

        return new CompilerOutput(matcher.group(1), matcher.group(2), parseInt(matcher.group(3), line));
    }

    public CompilerErrorOrWarn getErrorOrWarning()
    {
        return CompilerErrorOrWarn.getErrorOrWarning(message);
    }

    public int getLineNumber()
    {
        return lineNo;
    }

    public String getMessage()
    {
        return message;
    }

    public String getPath()
    {
        return path;
    }

    /**
     * does this object represent the useless <code>BEGIN failed--compilation aborted</code> compiler message?
     *
     * @return <code>true</code> if error message, <code>false</code> otherwise
     */
    public boolean isCompilationAborted()
    {
        return (message.indexOf("BEGIN failed--compilation aborted") == 0);
    }

    public boolean isLocal()
    {
        return "-".equals(path);
    }

    private static int parseInt(String str, String line)
    {
        try
        {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e)
        {
            logger.error("unable to parse line number from [{}]", line, e);
            return -1;
        }
    }
}
