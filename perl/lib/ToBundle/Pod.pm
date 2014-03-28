package ToBundle::Pod;
use base qw(ToBundle);

use strict;
use warnings;

sub _addData
{
    my $self = shift;
    my ($root, $node, $data) = @_;
    
    #
    # force the title into a scalar before prepping it - this isn't
    # necessary, but makes debugging easier...
    #    
    $data->{pattern} = $self->_prepPattern(scalar $node->title);
}

#
# get the name of the pod file to convert
# 
sub _getPodName
{
    die 'bad monkey, implement me!';
}

#sub _prepContent
#{
#    my $self = shift;
#    my ($text) = @_;
#    
#    # replace any double spaces w/ a single space
#    $text =~ s|\.\s{2,}|. |g;
#
#    return $text;
#}

sub _prepPattern
{
    my $self = shift;
    my ($text) = @_;
    
    # replace '(){}[]$?|*+' with '.'
    $text =~ s/([\(\)\{\}\[\]\$\?\|\*\+\\])/./g;

    # replace '%s %c %d %lx' with '.*?'
    $text =~ s/%([sdcl][x]{0,1})/.*?/g;

    # replace '%.[0-9]s' with '.*?'
    $text =~ s/%\.[0-9]s/.*?/g;

    return $self->SUPER::_strip_ws_and_nl($text);
}

## private

sub _getToConvert
{
    my $self = shift;
    
    my $pod  = $self->_getPodName;
    my $file = Pod::Find::pod_where({-inc => 1}, $pod);
    
    if (!$file)
    {
        Logger::error("unable to find pod for [%s], aborting...", $pod);
        exit(1);
    }    
    
    my $hash = $self->_getEmptyDataHash;
    $hash->{pom} = $self->_parsePod($file);
    
    return [$hash];
}

sub _getXmlElementTag
{
    return 'eow';
}

1;