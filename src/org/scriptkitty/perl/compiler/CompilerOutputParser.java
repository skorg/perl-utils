package org.scriptkitty.perl.compiler;

import org.scriptkitty.perl.util.IErrorProxy;
import org.scriptkitty.perl.util.NullErrorProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class CompilerOutputParser
{
    //~ Static fields/initializers

    private static final Pattern COMPILER = Pattern.compile("^(.*) at (\\S+) line (\\d+)[\\.,]?");

    //~ Instance fields

    private IErrorProxy proxy;

    //~ Constructors

    public CompilerOutputParser()
    {
        this(NullErrorProxy.getInstance());
    }

    public CompilerOutputParser(IErrorProxy proxy)
    {
        this.proxy = proxy;
    }

    //~ Methods

    public List<CompilerOutput> parse(List<String> lines)
    {
        List<CompilerOutput> list = new ArrayList<CompilerOutput>();

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

    public CompilerOutput parse(String line)
    {
        Matcher matcher = COMPILER.matcher(line);
        if (!matcher.find())
        {
            proxy.logError("unable to parse line [" + line + "] against pattern [" + COMPILER.pattern() + "]");
            return null;
        }

        String message = matcher.group(1);
        String path = matcher.group(2);
        int lineNo = parseInt(matcher.group(3), line);

        return new CompilerOutput(message, path, lineNo);
    }

    private int parseInt(String str, String line)
    {
        try
        {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e)
        {
            proxy.logError("unable to parse line number from [" + line + "]", e);
            return -1;
        }
    }
}
