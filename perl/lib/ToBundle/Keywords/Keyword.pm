package ToBundle::Keywords::Keyword;
use base qw(ToBundle::Keywords);

use strict;
use warnings;

# meh, see '_getType'
use Perl::Critic::Utils qw(:classification);

sub _addData
{
    my $self = shift;
    my ($root, $node, $data) = @_;

    $self->SUPER::_addData($root, $node, $data);
    
    $data->{bareword} = _is_bareword($data->{keyword}); 
}

sub _getBundleName
{
    return 'keywords';
}

sub _getKeywords
{
    return [@B::Keywords::Functions, @B::Keywords::Barewords];
}

sub _getPDocArg
{
    return '-f';
}

sub _getType
{
    my $self = shift;
    my ($item, $data) = @_;

    my $keyword = $data->{keyword};

    #
    # for the record, i loathe importing methods into a namespace, however,
    # given that the method names in Perl::Critic::Utils are slightly (and
    # by slightly, i mean a lot) unwieldy, this will allow for cleaner code
    #
    if (is_perl_builtin_with_list_context($keyword))
    {
        return 'L';
    }

    if (is_perl_builtin_with_multiple_arguments($keyword))
    {
        return 'M';
    }

    if (is_perl_builtin_with_no_arguments($keyword))
    {
        return 'N';
    }

    if (is_perl_builtin_with_one_argument($keyword))
    {
        return 'R';
    }

    if (is_perl_builtin_with_optional_argument($keyword))
    {
        return 'O';
    }
    
    return 'NULL';
}

sub _getXmlElementTag
{
    return 'keyword';
}

sub _getXmlRootTag
{
    return 'keywords';
}

sub _is_bareword
{
    my ($word) = @_;
    return (grep {$_ eq $word} @B::Keywords::Barewords) ? 'true' : 'false';
}

1;
