package ToBundle::Keyword::Variables;
use base qw(ToBundle::Keyword);

use strict;
use warnings;

use B::Keywords;

sub _escapeKeyword
{
    my $self = shift;
    my ($keyword) = @_;

    # change $= -> $\=
    $keyword =~ s/(.*?)\=/$1\\=/;
    # change $: -> $\:
    $keyword =~ s/(.*?)\:/$1\\:/;
    # change $# -> $\#
    $keyword =~ s/(.*?)\#:/$1\\#/;

    return $keyword;
}

sub _getKeywords
{
    return [
        @B::Keywords::Scalars,
        @B::Keywords::Arrays,
        @B::Keywords::Hashes,
        @B::Keywords::Filehandles
    ];
}

sub _getPDocArg
{
    return '-v';
}

1;