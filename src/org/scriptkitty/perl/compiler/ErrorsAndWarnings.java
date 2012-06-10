package org.scriptkitty.perl.compiler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;


public class ErrorsAndWarnings
{
    //~ Static fields/initializers

    private static String ERROR = "No explanation available for this error message";

    private static ErrorsAndWarnings self;

    private static ErrorOrWarning UNKNOWN_ERROR = new ErrorOrWarning("", ERROR, true);

    //~ Instance fields

    private Set<ErrorOrWarning> messages;

    //~ Constructors

    private ErrorsAndWarnings()
    {
        messages = new HashSet<ErrorOrWarning>();

        ResourceBundle bundle = ResourceBundle.getBundle("resources.errorsAndWarnings");
        for (String key : bundle.keySet())
        {
            String value = bundle.getString(key);
            String[] parts = value.split("\\t", 2);

            messages.add(new ErrorOrWarning(parts[0], parts[1], false));
        }
    }

    //~ Methods

    public static ErrorOrWarning getErrorOrWarning(String line)
    {
        if (self == null)
        {
            self = new ErrorsAndWarnings();
        }

        return self.get(line);
    }

    private ErrorOrWarning get(String line)
    {
        for (Iterator<ErrorOrWarning> i = messages.iterator(); i.hasNext();)
        {
            ErrorOrWarning eom = i.next();
            if (eom.matches(line))
            {
                return eom;
            }
        }

        return UNKNOWN_ERROR;
    }

    //~ Inner Classes

    public static class ErrorOrWarning
    {
        private String desc;
        private Pattern pattern;
        private boolean unknown;

        private ErrorOrWarning(String regexp, String desc, boolean unknown)
        {
            this.desc = desc;
            this.unknown = unknown;

            this.pattern = Pattern.compile(regexp);
        }

        public String getDescription()
        {
            return desc;
        }

        public boolean isUnknown()
        {
            return unknown;
        }

        public boolean isWarning()
        {
            return desc.startsWith("(W") || desc.startsWith("(D") || desc.startsWith("(S");
        }
        
        private boolean matches(String line)
        {
            return pattern.matcher(line).find();
        }
    }
}
