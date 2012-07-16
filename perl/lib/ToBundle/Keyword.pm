package ToBundle::Keyword;
use base qw(ToBundle);

use strict;
use warnings;

my @PERLDOC_FILTERS = (
    qr/^__/,
    qr/^\-/
);

#
# escape the keyword
# 
sub _escapeKeyword
{
    # default returns keyword
    return $_[1];
}

#
# get keywords - arrayref
#
sub _getKeywords
{
    die 'bad monkey, implement me!';
}

#
# perldoc arg
#
sub _getPDocArg
{
    die 'bad monkey, implement me!';
}

## private

sub _combineTitles
{
    return 1;
}

sub _getDestDir
{
    return 'lang';
}

sub _getEmptyKey
{
    my $self = shift;
    my ($data) = @_;
    
    return $data->{keyword};
}

sub _getItems
{
    my $self = shift;
    my ($root) = @_;
    
    return $root->item; 
}

sub _getPodKey
{
    my $self = shift;
    my ($data) = @_;
    
    return $self->_escapeKeyword($data->{keyword});
}

sub _getRoot
{
    my $self = shift;
    my ($pom) = @_;
    
    return $pom->over->[0];
}

sub _getToConvert
{
    my $self = shift;
    
    my @data = ();
    my $flag = $self->_getPDocArg;
    
    foreach my $keyword(@{$self->_getKeywords})
    {
        push @data, {keyword => $keyword};
        
        if (grep {$keyword =~ m/^$_/} @PERLDOC_FILTERS)
        {
#            printf STDERR "skipping keyword [%s], matched filter\n", $keyword;        
            next;                
        }
    
        my $command = sprintf 'perldoc -uT %s %s', $flag, quotemeta($keyword);         
        my $perldoc = `$command`;

        if (!$perldoc)
        {
#            printf STDERR "skipping keyword [%s], no perldoc found\n", $keyword;
            next;
        }        
    
        $data[-1]->{pom} = $self->_parsePod($perldoc);
    }
    
    return \@data;
}

sub _nextItemHasContent
{
    return 1;
}

sub _prepContent
{
    my $self = shift;
    my ($text) = @_;
    
    # take just the first part of the pod for the annotation
    $text =~ s|(.*?\.)\n\n.*|$1|sm;

    # escape slashes
    $text =~ s|\\|\\\\|g;
    # escape newlines   
    $text =~ s|\n|\\n|g;
    
    # replace any double spaces w/ a single space
    $text =~ s|\.\s{2,}|. |g;
    
    return $text;
}

sub _prepTitle
{
    my $self = shift;
    my ($text) = @_;
    
    # remove the index fields
    $text =~ s|X\<.*\>\s*||g;
    
    # trim trailing/leading whitespace
    $text =~ s|^\s+||g;
    $text =~ s|\s+$||g;

    # trim trailing newlines
    $text =~ s|\n$||g;
    
    return $text;
}

1;