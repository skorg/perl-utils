package ToBundle::Pod::PodChecker;
use base qw(ToBundle::Pod);

sub _addData
{
    my $self = shift;
    my ($root, $node, $data) = @_;
            
    $self->SUPER::_addData($root, $node, $data);
    
    $data->{type} = substr $root->title, 0, 1;
}

sub _getBundleName
{
    return 'podErrorsAndWarnings';
}

sub _getDestDir
{
    return 'pod'
}

sub _getNodes
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
    my ($pom, $data) = @_;

    return $pom->head1->[4]->head2;
}

sub _getXmlRootTag
{
    return 'podChecker';
}

#sub _prepContent
#{
#    my $self = shift;
#    my ($text) = @_;
#
#    $text =~ s|=begin _disabled_.*=end _disabled_||s;
#    
#    return $self->SUPER::_prepContent($text);
#}

sub _prepPattern
{
    my $self = shift;
    my ($text) = @_;
    
    $text =~ s/^\*//;
    
    return $self->SUPER::_prepPattern($text);
}

1;