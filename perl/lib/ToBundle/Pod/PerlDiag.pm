package ToBundle::Pod::PerlDiag;
use base qw(ToBundle::Pod);

sub _getBundleName
{
    return 'compilerErrorsAndWarnings';
}

sub _getDestDir
{
    return 'compiler'
}

sub _getItems
{
    my $self = shift;
    my ($root) = @_;
    
    return $root;
}

sub _getPodName
{
    return 'perldiag';
}

sub _getRoot
{
    my $self = shift;
    my ($pom) = @_;
    
    return $pom->head1->[1]->over->[0]->item;
}

sub _getType
{
    my $self = shift;
    my ($item) = @_;

    if ($item->content =~ m|^\((\w)|)
    {
        return $1;
    }
    
    #
    # meh, not every message in perldiag has a classification (annoying), so
    # we label it as 'U'nkown
    #
    return 'U';
}

sub _getXmlRootTag
{
    return 'perlDiag';
}

1;