package org.scriptkitty.perl.exec;

public class ProcessException extends RuntimeException
{
    //~ Static fields/initializers

    private static final long serialVersionUID = -3239077264509585378L;

    //~ Constructors

    public ProcessException(Throwable t)
    {
        super(t);
    }
}
