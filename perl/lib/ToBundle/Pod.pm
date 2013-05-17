package ToBundle::Pod;
use base qw(ToBundle);

use strict;
use warnings;

use Hash::Util;
use IO::File;

use Pod::Find;

#
# get the name of the pod file to convert
# 
sub _getPodName
{
    die 'bad monkey, implement me!';
}

sub _prepContent
{
    my $self = shift;
    my ($text) = @_;

    # trim leading/trailing whitespace
    $text =~ s|^\s+||g;
    $text =~ s|\s+$||g;

    # trim leading/trailing newlines
    $text =~ s|^\r*\n||;
    $text =~ s|\r*\n$||;
    
    return $self->SUPER::_prepContent($text);
}

sub _prepTitle
{
    my $self = shift;
    my ($text) = @_;
    
    # replace any leading white space
    $text =~ s/^\s//g;

    # replace '(){}[]$?|*+' with '.'
    $text =~ s/([\(\)\{\}\[\]\$\?\|\*\+\\])/./g;

    # replace '%s %c %d %lx' with '.*?'
    $text =~ s/%([sdcl][x]{0,1})/.*?/g;

    # replace '%.[0-9]s' with '.*?'
    $text =~ s/%\.[0-9]s/.*?/g;

    return $text;    
}

## private

sub _getToConvert
{
    my $self = shift;
    
    my $pod  = $self->_getPodName;
    my $file = Pod::Find::pod_where({-inc => 1}, $pod);
    
    if (!$file)
    {
        print STDERR "unable to find pod for '$pod', aborting...\n";
        exit(1);
    }    
    
    return [{pom => $self->_parsePod($file)}];
}

sub _getXmlElementTag
{
    return 'eow';
}

sub _writeTitles
{
    my $self = shift;
    my ($writer, $titles) = @_;
    
    $writer->startTag('patterns');
    foreach my $title (@{$titles})
    {
        $writer->cdataElement('pattern', $title);
    }
    $writer->endTag;
}

1;