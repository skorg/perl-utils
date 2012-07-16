package ToBundle::Keyword::Words::Functions;
use base qw(ToBundle::Keyword::Words);

use strict;
use warnings;

use B::Keywords;

sub _getBundleName
{
    return 'functionKeywords';
}

sub _getKeywords
{
    return [@B::Keywords::Functions];
}

1;