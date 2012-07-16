package ToBundle::Keyword::Variable::Hash;
use base qw(ToBundle::Keyword::Variable);

use strict;
use warnings;

use B::Keywords;

sub _getBundleName
{
    return 'hashKeywords';
}

sub _getKeywords
{
    return [@B::Keywords::Hashes];
}

1;