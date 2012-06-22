use strict;
use warnings;

use lib qw(lib);

use ToBundle::Pod::PerlDiag;
use ToBundle::Pod::PodChecker;

my @TO_GENERATE = (
    {
        filename    => 'errorsAndWarnings',
        converter   => 'ToBundle::Pod::PerlDiag',
    },
    {
        filename    => 'podErrorsAndWarnings',
        converter   => 'PodToBundle::PodChecker',
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
