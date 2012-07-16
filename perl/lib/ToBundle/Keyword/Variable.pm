package ToBundle::Keyword::Variable;
use base qw(ToBundle::Keyword);

use strict;
use warnings;

sub _escapeKeyword
{
    my $self = shift;
    my ($keyword) = @_;
    
    # change $= -> $\=
    $keyword =~ s/(.*?)\=/$1\\=/;
    # change $: -> $\:
    $keyword =~ s/(.*?)\:/$1\\:/;
    # change $# -> $\#
    $keyword =~ s/(.*?)\#/$1\\#/;

    # change %! -> %\!, $! -> $\!
    $keyword =~ s|(.*?)!|$1\\!|;


    return $keyword;
}

sub _getPDocArg
{
    return '-v';
}

sub _getPodValue
{
    my $self = shift;
    my ($title, $content, $extra) = @_;
    
    return sprintf "%s|%s", $title, $content;
}

1;