package org.scriptkitty.perl.internal;

import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlElement;


public abstract class AbstractErrorOrWarn
{
    protected static final String MSG = "No explanation available for this message";

    private Pattern pattern;

    @XmlElement private String content;

    protected AbstractErrorOrWarn()
    {
        setPattern("");
        this.content = "";
    }

    public final boolean matches(String line)
    {
        return pattern.matcher(line).find();
    }

    @Override public final String toString()
    {
        return pattern.toString();
    }

    protected static <T extends AbstractErrorOrWarn> T get(String line, ResourceBundle bundle, T unknown)
    {
        for (String key : bundle.keySet())
        {
            @SuppressWarnings("unchecked")
            T eow = (T) bundle.getObject(key);
            if (eow.matches(line))
            {
                return eow;
            }
        }

        return unknown;
    }

    protected final String getContent()
    {
        // TODO: determine how to handle pod content
        return content;
    }

    @XmlElement(name = "pattern")
    private void setPattern(String regexp)
    {
        this.pattern = Pattern.compile(regexp);
    }
}
