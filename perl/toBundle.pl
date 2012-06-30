use strict;
use warnings;

BEGIN
{
    use File::Util;
    
    if (index(__FILE__, File::Util->SL) != -1)
    {
        printf "pls 'cd' into the script dir before running\n";
        exit(1);
    }
}

use lib qw(lib);

use ToBundle::Keyword::Variables;
use ToBundle::Keyword::Words;

use ToBundle::Pod::PerlDiag;
use ToBundle::Pod::PodChecker;

my @TO_GENERATE = (
    {
        converter   => 'ToBundle::Pod::PerlDiag',
        filename    => 'errorsAndWarnings',
        srcDirName  => 'compiler'        
    },
    {
        converter   => 'ToBundle::Pod::PodChecker',
        filename    => 'podErrorsAndWarnings',
        srcDirName  => 'pod'    
    },
    {
        converter   => 'ToBundle::Keyword::Variables',
        filename    => 'variableKeywords',
        srcDirName  => 'lang'        
    },
    {
        converter   => 'ToBundle::Keyword::Words',
        filename    => 'wordKeywords',
        srcDirName  => 'lang'        
    },
);

foreach my $generate (@TO_GENERATE)
{
    my $converter = $generate->{converter}->new(
        dir  => $generate->{srcDirName},
        name => $generate->{filename}
    );

    $converter->convert;
}
