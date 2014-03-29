package org.scriptkitty.perl.internal;

import java.util.ResourceBundle;

import javax.xml.bind.annotation.XmlElement;


public abstract class AbstractKeyOrSym
{
    //~ Instance fields

    @XmlElement private final String content;

    @XmlElement protected final String keyword;

    //~ Constructors

    protected AbstractKeyOrSym()
    {
        this.keyword = "";
        this.content = "";
    }

    //~ Methods

    public abstract boolean isNull();

    /*
     * @see java.lang.Object#toString()
     */
    @Override public final String toString()
    {
        return keyword;
    }

    protected static <T extends AbstractKeyOrSym> T get(String keyword, ResourceBundle bundle, T unknown)
    {
        if (!bundle.containsKey(keyword))
        {
            return unknown;
        }

        return (T) bundle.getObject(keyword);
    }

    protected final String getContent()
    {
        // TODO: determine how to handle pod content
        return content;
    }
}
