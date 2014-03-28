use strict;
use warnings;

BEGIN
{
    # TODO: this should be 'eval-ed' to make sure it exists
    use File::Util;
    
    if (index(__FILE__, File::Util->SL) != -1)
    {
        printf "pls 'cd' into the script dir before running\n";
        exit(1);
    }
}

use lib qw(lib);

use ToBundle::Keywords::Keyword;
use ToBundle::Keywords::Symbol;

use ToBundle::Pod::PerlDiag;
use ToBundle::Pod::PodChecker;

my @TO_GENERATE = qw( 
    ToBundle::Keywords::Keyword
    ToBundle::Keywords::Symbol

    ToBundle::Pod::PerlDiag
    ToBundle::Pod::PodChecker              
);

foreach my $class (@TO_GENERATE)
{
    $class->new()->convert;
}
