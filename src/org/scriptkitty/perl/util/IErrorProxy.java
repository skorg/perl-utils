package org.scriptkitty.perl.util;

public interface IErrorProxy
{
    void logError(String error);
    void logError(String error, Throwable t);
}
