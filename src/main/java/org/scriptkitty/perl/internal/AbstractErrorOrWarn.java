package org.scriptkitty.perl.internal;

import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlElement;


public class AbstractErrorOrWarn
{
    //~ Static fields/initializers

    protected static final String MSG = "No explanation available for this message";

    //~ Instance fields

    private Pattern pattern;

    @XmlElement private String content;

    //~ Constructors

    protected AbstractErrorOrWarn()
    {
        // required by jaxb
    }

//    public final String getContent()
//    {
//        return content;
//    }

    protected AbstractErrorOrWarn(String regexp, String content)
    {
        this.content = content;
        setPattern(regexp);
    }

    //~ Methods

    public final String getPattern()
    {
        return pattern.toString();
    }

    public final boolean matches(String line)
    {
        return pattern.matcher(line).find();
    }

    protected static <T extends AbstractErrorOrWarn> T get(String line, ResourceBundle bundle, T unknown)
    {
        for (String key : bundle.keySet())
        {
            T eow = (T) bundle.getObject(key);
            if (eow.matches(line))
            {
                return eow;
            }
        }

        return unknown;
    }

    @XmlElement(name = "pattern")
    private void setPattern(String regexp)
    {
        this.pattern = Pattern.compile(regexp);
    }
}
