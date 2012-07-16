package ToBundle::Keyword::Words;
use base qw(ToBundle::Keyword);

use strict;
use warnings;

# meh, see '_determine_builtin_type'
use Perl::Critic::Utils qw(:classification);

sub _determine_builtin_type
{
    my ($keyword) = @_;

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
    
    return 'Z';
}

sub _getPDocArg
{
    return '-f';
}

sub _getType
{
    my $self = shift;
    my ($root, $data) = @_;

    return _determine_builtin_type($data->{keyword});
}

1;
