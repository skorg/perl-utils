package ToBundle::Pod::PerlDiag;
use base qw(ToBundle::Pod);

use strict;
use warnings;

sub _addData
{
    my $self = shift;
    my ($root, $node, $data) = @_;

    $self->SUPER::_addData($root, $node, $data);

    $data->{classifications} = _getClassifications($self, $node);
}

sub _getBundleName
{
    return 'errorsAndWarnings';
}

sub _getClassifications
{
    my $self = shift;
    my ($node) = @_;

    #
    # there's probably a better way to do this, but brute force has it
    # right now...
    #
    my @types   = ();
    my $content = $node->content;

    if ($content =~ m|^\(|)
    {
        foreach my $c (split "\\)\\(", (split "\\)\\s[\\w|\\n]", $content)[0])
        {            
            $c =~ s|\(||;

            if ($c =~ m|(\w)\s(.*)|)
            {
                my ($type, $pragmas) = ($1, $2);
                $pragmas =~ s|\s||g;
            
                push @types, {$type => [split ',', $pragmas]};
            }
            else
            {
                push @types, {$c => undef};  
            }
        }
    }
    else
    {
        #
        # meh, not every message in perldiag has a classification (annoying),
        # so we label it as 'U'nkown w/ no pragmas
        #
        push @types, {U => undef};
    }

    return \@types;
}

sub _getDestDir
{
    return 'compiler';
}

sub _getNodes
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

sub _getXmlRootTag
{
    return 'compiler';
}

#sub _prepContent
#{
#    my $self = shift;
#    my ($text) = @_;
#
#    $text =~ s|=item\s||s;
#    
#    return $self->SUPER::_prepContent($text);
#}

sub _writeBundleSpecificElements
{
    my $self = shift;
    my ($writer, $data) = @_;

    my $classifications = delete $data->{classifications};

    $writer->startTag('classifications');
    foreach my $entry (@{$classifications})
    {
        my ($type, $pragmas) = %{$entry};

        $writer->startTag('classification');
        $writer->dataElement('type', $type);
        
        if ($pragmas)
        {
            $writer->startTag('pragmas');
            foreach my $pragma (@{$pragmas})
            {
                $writer->dataElement('pragma', $pragma);
            }
            $writer->endTag;   
        }
        $writer->endTag;
    }

    $writer->endTag;
    
    $self->SUPER::_writeBundleSpecificElements($writer, $data);
}

1;
