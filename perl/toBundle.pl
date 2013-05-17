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

use ToBundle::Keyword::Words::Barewords;
use ToBundle::Keyword::Words::Functions;

use ToBundle::Keyword::Variable::Array;
use ToBundle::Keyword::Variable::FileHandle;
use ToBundle::Keyword::Variable::Hash;
use ToBundle::Keyword::Variable::Scalar;

use ToBundle::Pod::PerlDiag;
use ToBundle::Pod::PodChecker;

#my @TO_GENERATE = qw(
#    ToBundle::Keyword::Variable::Array  
#);

my @TO_GENERATE = qw( 
    ToBundle::Keyword::Words::Barewords
    ToBundle::Keyword::Words::Functions
   
    ToBundle::Keyword::Variable::Array
    ToBundle::Keyword::Variable::FileHandle
    ToBundle::Keyword::Variable::Hash
    ToBundle::Keyword::Variable::Scalar    

    ToBundle::Pod::PerlDiag
    ToBundle::Pod::PodChecker               
);

foreach my $class (@TO_GENERATE)
{
    $class->new()->convert;
}
