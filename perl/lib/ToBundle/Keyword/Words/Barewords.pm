package ToBundle::Keyword::Words::Barewords;
use base qw(ToBundle::Keyword::Words);

use strict;
use warnings;

use B::Keywords;

sub _getBundleName
{
    return 'barewordKeywords';
}

sub _getKeywords
{    
    return [@B::Keywords::Barewords];
}

1;