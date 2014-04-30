package org.scriptkitty.perl.internal;

public interface IContentManager
{
    StaticContentProvider<?> getContentProvider(String baseName) throws InstantiationException;
}
