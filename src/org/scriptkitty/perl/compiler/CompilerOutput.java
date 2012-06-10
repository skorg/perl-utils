package org.scriptkitty.perl.compiler;

public final class CompilerOutput
{
    public final String message;
    public final String path;
    public final int lineNo;

    CompilerOutput(String message, String path, int lineNo)
    {
        this.message = message;
        this.path = path;
        this.lineNo = lineNo;
    }

    public boolean isLocal()
    {
        return "-".equals(path);
    }
    
    /**
     * does this object represent the useless <code>BEGIN failed--compilation aborted</code> compiler message?
     * 
     * @return <code>true</code> if error message, <code>false</code> otherwise
     */
    public boolean isCompilationAborted()
    {
        return (message.indexOf("BEGIN failed--compilation aborted") == 0);
    }
    
    public ErrorsAndWarnings.ErrorOrWarning getErrorOrWarning()
    {
        return ErrorsAndWarnings.getErrorOrWarning(message);
    }
}
