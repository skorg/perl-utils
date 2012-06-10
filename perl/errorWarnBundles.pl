use strict;
use warnings;

use lib qw(lib);

use PodToBundle::PerlDiag;
use PodToBundle::PodChecker;

my @TO_GENERATE = (
    {
        filename  => 'errorsAndWarnings',
        converter => 'PodToBundle::PerlDiag' 
    },
    {
        filename  => 'podErrorsAndWarnings',
        converter => 'PodToBundle::PodChecker' 
    },
);

foreach my $generate (@TO_GENERATE)
{
    my $converter = $generate->{converter}->new(
        dir  => $ARGV[0],
        name => $generate->{filename}
    );

    $converter->validateDir;
    $converter->convert;
}
