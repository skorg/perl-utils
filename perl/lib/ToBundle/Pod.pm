package ToBundle::Pod;
use base qw(ToBundle);

use strict;
use warnings;

use Hash::Util;
use IO::File;

use Pod::Find;

#
# get the actual content items - in some cases, this may just be $root' 
# itself, other times further traversal will be required
#
sub _getItems
{
    my $self = shift;
    my ($root) = @_;
    
    die 'bad monkey, implement me!';
}

#
# get the name of the pod file to convert
# 
sub _getPodName
{
    die 'bad monkey, implement me!';
}

#
# get the 'type'...error, warning, etc
#
sub _getType
{
    my $self = shift;
    my ($item) = @_;
    
    die 'bad monkey, implement me!';
}

#
# get the 'root' item node that contains the data we want 
#
sub _getRoot
{
    my $self = shift;
    my ($pom) = @_;
    
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
    
    # escape slashes
    $text =~ s|\\|\\\\|g;
    # escape newlines   
    $text =~ s|\r*\n|\\n|g;
    
    # replace any double spaces w/ a single space
    $text =~ s|\.\s{2,}|. |g;
    
    return $text;
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

sub _getPodKey
{
    return shift->_idx;
}

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

sub _toEntry
{
    my $self = shift;
    my ($title, $content, $extra) = @_;

    my $type = $extra->{type};
    
    return sprintf "%s=%s|%s|%s", $self->_idx, $type, $title, $content;
}

1;