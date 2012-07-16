package ToBundle::Keyword::Variable::FileHandle;
use base qw(ToBundle::Keyword::Variable);

use strict;
use warnings;

use B::Keywords;

sub _getBundleName
{
    return 'fileHandleKeywords';
}

sub _getKeywords
{
    return [@B::Keywords::Filehandles];
}

1;