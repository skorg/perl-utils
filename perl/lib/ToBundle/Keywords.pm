package ToBundle::Keywords;
use base qw(ToBundle);

use strict;
use warnings;

my @PERLDOC_FILTERS = (
    qr/^__/,
    qr/^\-/
);

sub _addData
{
    my $self = shift;
    my ($root, $node, $data) = @_;

    # figure out what type we are - error, warning, etc...
    if (my $type = $self->_getType($node, $data))
    {
        $data->{type} = $type;
    }
}

sub _convert
{
    my $self = shift;
    my ($data) = @_;
    
    if (exists $data->{pom})
    {
        $self->SUPER::_convert($data);
    }
    else
    {
        $self->_addData(undef, undef, $data);
        $self->_write(undef, $data);
    }
}

sub _getDestDir
{
    return 'lang';
}

sub _getKeywords
{
    die 'bad monkey, implement me!';
}

sub _getNodes
{
    my $self = shift;
    my ($root) = @_;
    
    return $root;
}

#
# perldoc arg
#
sub _getPDocArg
{
    die 'bad monkey, implement me!';
}

sub _getRoot
{
    my $self = shift;
    my ($pom) = @_;
    
    return $pom;
}

sub _getToConvert
{
    my $self = shift;
    
    my @data = ();
    my $flag = $self->_getPDocArg;
    
    foreach my $keyword (@{$self->_getKeywords})
    {
        push @data, $self->_getEmptyDataHash;
        $data[-1]->{keyword} = $keyword;
        
        if (grep {$keyword =~ m/^$_/} @PERLDOC_FILTERS)
        {
            Logger::debug("skipping pod query for keyword [%s]", $keyword); 
            next;                
        }
    
        my $command = sprintf 'perldoc -uT %s %s', $flag, quotemeta($keyword);         
        my $perldoc = `$command 2>/dev/null`;

        if (!$perldoc)
        {
            Logger::debug("skipping keyword [%s], no perldoc found", $keyword);
            next;
        }        
    
        $data[-1]->{pom} = $self->_parsePod($perldoc);
    }
    
    return \@data;
}

1;