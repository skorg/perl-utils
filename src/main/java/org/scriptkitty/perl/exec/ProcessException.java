package org.scriptkitty.perl.exec;

public class ProcessException extends RuntimeException
{
    private static final long serialVersionUID = -3239077264509585378L;

    public ProcessException(Throwable t)
    {
        super(t);
    }
}
