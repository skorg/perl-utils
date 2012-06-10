package org.scriptkitty.perl.util;

public class NullErrorProxy implements IErrorProxy
{
    //~ Static fields/initializers

    private static IErrorProxy self;

    //~ Methods

    public static IErrorProxy getInstance()
    {
        if (self == null)
        {
            self = new NullErrorProxy();
        }

        return self;
    }

    @Override public void logError(String error)
    {
        // no-op
    }

    @Override public void logError(String error, Throwable t)
    {
        // no-op
    }
}
