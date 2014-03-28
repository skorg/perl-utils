package org.scriptkitty.perl.exec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.util.Arrays;
import java.util.List;


public final class ProcessHandler
{
    //~ Instance fields

    private boolean rethrow;

    private final Object lock = new Object();

    private Process process;

    private String stdin;

    //~ Constructors

    private ProcessHandler(Process process, String stdin, boolean rethrow)
    {
        this.process = process;
        this.stdin = stdin;
    }

    //~ Methods

    public static ProcessHandler getInstance(Process process, String stdin)
    {
        return new ProcessHandler(process, stdin, false);
    }

    public static ProcessHandler getInstance(Process process, String stdin, boolean rethrow)
    {
        return new ProcessHandler(process, stdin, rethrow);
    }

    public ProcessResult getResult() throws ProcessException
    {
        /*
         * NOTE: all readers/writers are named from the executed script's perpective
         */
        ScriptOutputReader stdout = new ScriptOutputReader(process.getInputStream());
        ScriptOutputReader stderr = new ScriptOutputReader(process.getErrorStream());

        try
        {
            Thread stdoutThread = new Thread(stdout);
            Thread stderrThread = new Thread(stderr);

            stdoutThread.start();
            stderrThread.start();

            // the output stream is our conduit to stdin
            writeToStdin(process.getOutputStream(), stdin);

            synchronized (lock)
            {
                try
                {
                    lock.wait();
                    process.waitFor();
                }
                catch (InterruptedException e)
                {
                    // do nothing
                }
            }
        }
        catch (IOException e)
        {
            if (rethrow)
            {
                throw new ProcessException(e);
            }
        }
        finally
        {
            // make sure the process is terminated!
            process.destroy();
        }

        // sleep so the process exits cleanly...
        sleep(1);

        return new ProcessResult(process.exitValue(), stdout.getBuffer(), stderr.getBuffer());
    }

    private void closeWriter(OutputStreamWriter writer)
    {
        if (writer != null)
        {
            try
            {
                writer.close();
            }
            catch (IOException e)
            {
                // do nothing
            }
        }
    }

    private void sleep(int time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException e)
        {
            // do nothing
        }
    }

    private void writeToStdin(OutputStream stream, String stdin) throws IOException
    {
        OutputStreamWriter stdinWriter = new OutputStreamWriter(stream);

        try
        {
            if ((stdin != null) && (stdin.length() > 0))
            {
                stdinWriter.write(stdin.toCharArray());
                stdinWriter.flush();
            }
        }
        finally
        {
            closeWriter(stdinWriter);
        }
    }

    //~ Inner Classes

    public static final class ProcessResult
    {
        public final int exitValue;

        public final String stderr;
        public final String stdout;

        private ProcessResult(int exitValue, String stdout, String stderr)
        {
            this.stdout = stdout;
            this.stderr = stderr;
            this.exitValue = exitValue;
        }

        public List<String> stderrLines()
        {
            return toList(stderr);
        }

        public List<String> stdoutLines()
        {
            return toList(stdout);
        }

        private List<String> toList(String str)
        {
            return Arrays.asList(str.split("[\r\n]+"));
        }
    }

    private class ScriptOutputReader implements Runnable
    {
        private InputStream stream;
        private StringBuffer buffer = new StringBuffer();

        ScriptOutputReader(InputStream stream)
        {
            this.stream = stream;
        }

        @Override public void run()
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            try
            {
                int count = 0;
                char[] bytes = new char[1024];

                while ((count = reader.read(bytes)) >= 0)
                {
                    buffer.append(bytes, 0, count);
                }

                synchronized (lock)
                {
                    lock.notifyAll();
                }
            }
            catch (IOException e)
            {
                buffer.setLength(0);
            }
        }

        String getBuffer()
        {
            return buffer.toString();
        }
    }
}
