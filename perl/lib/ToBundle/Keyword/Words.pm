package ToBundle::Keyword::Words;
use base qw(ToBundle::Keyword);

use strict;
use warnings;

use B::Keywords;

# meh, see '_determine_builtin_type'
use Perl::Critic::Utils qw(:classification);

sub _getKeywords
{
    return [
        @B::Keywords::Functions,
        @B::Keywords::Barewords
    ];
}

sub _getPDocArg
{
    return '-f';
}

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

    # is_perl_builtin_with_optional_argument($keyword))
    return 'O';    
}
#
#sub _write_entry
#{
#    my $self = shift;
#    my ($file, $keyword, $docs) = @_; 
#    
#    my $type = _determine_builtin_type($keyword);
#        
##    printf STDERR "%s=%s|%s\n", $keyword, $type, $docs;
#    printf $file "%s=%s|%s\n", $keyword, $type, $docs;
#}

1;