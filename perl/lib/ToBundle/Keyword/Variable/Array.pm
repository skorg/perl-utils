package ToBundle::Keyword::Variable::Array;
use base qw(ToBundle::Keyword::Variable);

use strict;
use warnings;

use B::Keywords;

sub _getBundleName
{
    return 'arrayKeywords';
}

sub _getKeywords
{
    #
    # meh, B::Keywords has @LAST_MATCH_END as $LAST_MATCH_END
    # 
    my @list = @B::Keywords::Arrays;    
    foreach my $keyword (@list)
    {
        $keyword =~ s|^\$|@|;
    }

    return \@list;
}

1;
