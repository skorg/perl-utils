package org.scriptkitty.perl.internal;

public interface IContentManager
{
    //~ Methods

    StaticContentProvider<?> getContentProvider(String baseName) throws InstantiationException;
}
