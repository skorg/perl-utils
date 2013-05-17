package ToBundle::Keyword;
use base qw(ToBundle);

use strict;
use warnings;

my @PERLDOC_FILTERS = (
    qr/^__/,
    qr/^\-/
);

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

sub _getDestDir
{
    return 'lang';
}

sub _getItems
{
    my $self = shift;
    my ($root) = @_;
    
    return $root->item; 
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

sub _getXmlElementTag
{
    return 'keyword';
}

sub _getXmlRootTag
{
    return 'keywords';
}

sub _prepContent
{
    my $self = shift;
    my ($text) = @_;
    
    # take just the first part of the pod for the annotation
    $text =~ s|(.*?\.)\n\n.*|$1|sm;
    
    return $self->SUPER::_prepContent($text);
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

sub _writeTitles
{
    my $self = shift;
    my ($writer, $titles) = @_;
    
    # no-op, for now...
}

1;