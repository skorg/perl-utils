package ToBundle::Keyword;
use base qw(ToBundle);

use strict;
use warnings;

my @PERLDOC_FILTERS = (
    qr/^__/,
    qr/^\-/
);

sub convert
{
    my $self = shift;

    my $i = 0;

    foreach my $keyword(@{$self->_getKeywords})
    {
        my $docs = _genPerlDoc($self, $keyword, $self->_getPDocArg);
        $keyword = $self->_escapeKeyword($keyword);
        
        $self->_write_entry($self->{fh}, $keyword, $docs);
    }
}

## sub-class

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

#
# write the entry
#
sub _write_entry
{
    my $self = shift;
    my $file = shift;
    my @args = @_;
        
#    printf STDERR "%s=%s\n", @args;
    printf $file "%s=%s\n", @args;
}

## private

sub _genPerlDoc
{
    my $self = shift;
    my ($keyword, $flag) = @_;

    if (!$flag)
    {
        die '_genPerlDoc invoked w/o passing $flag';
    }

    if (_matches_filter($keyword, \@PERLDOC_FILTERS))
    {
#        printf STDERR "skipping keyword [%s], matched filter\n", $keyword;
        return '';
    }

    my $command = sprintf 'perldoc %s %s', $flag, quotemeta($keyword); 
    my $perldoc = `$command`;

    if (!$perldoc)
    {
#        printf STDERR "skipping keyword [%s], no perldoc found\n", $keyword;
        return '';
    }

    # take just the first part of the pod for the annotation
    $perldoc =~ s/(.*?\.)\n\n.*/$1/sm;
   
    $perldoc = $self->_escapeSlashes($perldoc);
    $perldoc = $self->_escapeNewline($perldoc);
    
    return $perldoc;
}

sub _matches_filter
{
    my ($keyword, $filters) = @_;

    return grep {$keyword =~ m/^$_/} @{$filters};
}

1;