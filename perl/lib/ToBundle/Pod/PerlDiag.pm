package ToBundle::Pod::PerlDiag;
use base qw(ToBundle::Pod);

sub _getItems
{
    my $self = shift;
    my ($root) = @_;
    
    return ($root);
}

sub _getPodName
{
    return 'perldiag';
}

sub _getRoot
{
    my $self = shift;
    my ($pom) = @_;
    
    return ($pom->head1->[1]->over->[0]->item);
}

sub _getType
{
    my $self = shift;
    my ($item) = @_;
    
    return 'E';
}

1;