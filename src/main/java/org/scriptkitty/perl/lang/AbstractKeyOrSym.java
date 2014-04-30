package org.scriptkitty.perl.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.xml.bind.annotation.XmlElement;


abstract class AbstractKeyOrSym
{
    @XmlElement protected final String keyword;

    @XmlElement private final String content;

    protected AbstractKeyOrSym()
    {
        this.keyword = "";
        this.content = "";
    }

    public abstract boolean isNull();

    @Override public String toString()
    {
        return keyword;
    }

    protected static <T extends AbstractKeyOrSym> Collection<T> find(ResourceBundle bundle, IFindCallback<T> callback)
    {
        ArrayList<T> found = new ArrayList<>();
        Enumeration<String> keys = bundle.getKeys();

        while (keys.hasMoreElements())
        {
            T object = (T) bundle.getObject(keys.nextElement());
            if (callback.isWanted(object))
            {
                found.add(object);
            }
        }

        return found;
    }

    protected static <T extends AbstractKeyOrSym> T get(String keyword, ResourceBundle bundle, T unknown)
    {
        if (!bundle.containsKey(keyword))
        {
            return unknown;
        }

        return (T) bundle.getObject(keyword);
    }

    protected static interface IFindCallback<T extends AbstractKeyOrSym>
    {
        boolean isWanted(T object);
    }
}
