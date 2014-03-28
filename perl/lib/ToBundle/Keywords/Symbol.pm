package ToBundle::Keywords::Symbol;
use base qw(ToBundle::Keywords);

use strict;
use warnings;

use B::Keywords;

sub _getArrays
{
    #
    # meh, B::Keywords has @LAST_MATCH_END as $LAST_MATCH_END
    # 
    my @list = @B::Keywords::Arrays;    
    foreach my $keyword (@list)
    {
        $keyword =~ s|^\$|@|;
    }

    return @list;    
}

sub _getBundleName
{
    return 'symbols';
}

sub _getKeywords
{
    #
    # meh, B::Keywords has @LAST_MATCH_END as $LAST_MATCH_END
    # 
    push my @list, _getArrays();  
    push @list, @B::Keywords::Filehandles;
    push @list, @B::Keywords::Hashes;
    push @list, @B::Keywords::Scalars;

    return \@list;
}

sub _getPDocArg
{
    return '-v';
}

sub _getType
{
    my $self = shift;
    my ($item, $data) = @_;

    my $keyword = $data->{keyword};

    if (_matches($keyword, [_getArrays()]))
    {
        return 'A';
    }
    
    if (_matches($keyword, \@B::Keywords::Filehandles))
    {
        return 'F';
    }
    
    if (_matches($keyword, \@B::Keywords::Hashes))
    {
        return 'H';
    }

    # otherwise we're a scalar
    return 'S';
}

sub _getXmlElementTag
{
    return 'symbol';
}

sub _getXmlRootTag
{
    return 'symbols';
}

sub _matches
{
    my ($key, $array) = @_;
    return (grep {$_ eq $key} @{$array}) ? 1 : 0;
}

1;