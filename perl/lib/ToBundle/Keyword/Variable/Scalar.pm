package ToBundle::Keyword::Variable::Scalar;
use base qw(ToBundle::Keyword::Variable);

use strict;
use warnings;

use B::Keywords;

sub _getBundleName
{
    return 'scalarKeywords';
}

sub _getKeywords
{
    return [@B::Keywords::Scalars];
}

1;