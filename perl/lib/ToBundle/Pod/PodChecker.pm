package ToBundle::Pod::PodChecker;
use base qw(ToBundle::Pod);

sub _getBundleName
{
    return 'podErrorsAndWarnings';
}

sub _getDestDir
{
    return 'pod'
}

sub _getItems
{
    my $self = shift;
    my ($root) = @_;
    
    return $root->over->[0]->item;
}

sub _getPodName
{
    return 'Pod::Checker';
}

sub _getRoot
{
    my $self = shift;
    my ($pom) = @_;
    
    return $pom->head1->[4]->head2;
}

sub _getType
{
    my $self = shift;
    my ($item) = @_;
    
    return substr $item->title, 0, 1;
}

sub _nextItemHasContent
{
    return 1;
}

sub _prepTitle
{
    my $self = shift;
    my ($text) = @_;
    
    $text =~ s/^\*//;
    
    return $self->SUPER::_prepTitle($text);
}

1;