package Logger;

use strict;

use constant {
    DEBUG => 'DEBUG',
    INFO  => 'INFO',
    ERROR => 'ERROR',
};

sub debug
{
    if ($ENV{TO_BUNDLE_DEBUG})
    {
        _print(DEBUG, @_);
    }
}

sub error
{
    _print(ERROR, @_);
}

sub info
{
    _print(INFO, @_);
}

sub _print
{
    my ($level, $format, @args) = @_;
    
    for (my $i = 0; $i < scalar(@args); $i++)
    {
        if (ref $args[$i] eq 'CODE')
        {
            $args[$i] = $args[$i]->();
        }
    }
    
    printf "[%s] - %s\n", $level, sprintf("$format", @args);
}

1;