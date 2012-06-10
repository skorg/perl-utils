#
# generates resource bundles containing the perl keywords. each type of keyword
# is stored in a separate bundle so they can be easily categorized (and so
# they fit into the bundle name/value pair model).
#
use strict;

use B::Keywords qw();

use File::Spec qw();

use IO::File;

# meh, see '_determine_builtin_type'
use Perl::Critic::Utils qw(:classification);

use constant {
    SUFFIX => 'properties'
};

my @PERLDOC_FILTERS = (
    qr/^__/,
    qr/^\-/
);

my %LIST  = ();
my %MULTI = ();
my %NONE  = ();
my %ONE   = ();
my %OPT   = ();

#
# TODO: provide documentation where applicable
#
# perlvar will have defintions for scalars
#
my @TO_GENERATE = (
    {
        filename      => 'scalarKeywords',
        keywords      => sub {return @B::Keywords::Scalars},
        documentation => sub {return _genPerlDoc(@_, '-v')},
    },
    {
        filename      => 'arrayKeywords',
        keywords      => sub {return @B::Keywords::Arrays},
        documentation => sub {return _genPerlDoc(@_, '-v')},
    },
    {
        filename      => 'hashKeywords',
        keywords      => sub {return @B::Keywords::Hashes},
        documentation => sub {return _genPerlDoc(@_, '-v')},
    },
    {
        filename      => 'fileHandleKeywords',
        keywords      => sub {return @B::Keywords::Filehandles},
        documentation => sub {return _genPerlDoc(@_, '-v')},
    },
    {
        filename      => 'functionKeywords',
        keywords      => sub {return @B::Keywords::Functions},
        documentation => sub {return _genPerlDoc(@_, '-f')},
    },
    {
        keywords      => sub {return @B::Keywords::Barewords},
        filename      => 'barewordKeywords',
        documentation => sub {return _genPerlDoc(@_, '-f')},
    },
    #
    # this is a big cheat/hack, but it will work...
    #
    {
        filename      => 'builtinListContext',
        keywords      => sub {return (keys %LIST)},
        documentation => sub {return _genEmptyDoc(@_)},
    },
    {
        filename      => 'builtinMultiArgs',
        keywords      => sub {return (keys %MULTI, keys %LIST);},
        documentation => sub {return _genEmptyDoc(@_)},
    },
    {
        filename      => 'builtinNoArgs',
        keywords      => sub {return (keys %NONE);},
        documentation => sub {return _genEmptyDoc(@_)},
    },
    {
        filename      => 'builtinOneArg',
        keywords      => sub {return (keys %ONE);},
        documentation => sub {return _genEmptyDoc(@_)},
    },
    {
        filename      => 'builtinOptArgs',
        keywords      => sub {return (keys %OPT);},
        documentation => sub {return _genEmptyDoc(@_)},
    },
);

## main

my $dir = $ARGV[0];
_validateDir($dir);

foreach my $generate (@TO_GENERATE)
{
    my $file = _getOutputFileHandle($dir, $generate->{filename});
    my @keywords = $generate->{keywords}->();

    foreach my $keyword (@keywords)
    {
        _determine_builtin_type($keyword);

        my $docs = $generate->{documentation}->($keyword);
        $keyword = _escapeKeyword($keyword);

#        print STDERR sprintf "keyword: %s\ndocs: %s\n--\n", $keyword, $docs;
        
        
        
        printf $file "%s=%s\n", $keyword, $docs;
    }
    $file->close;
}

## private

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
        $LIST{$keyword} = 1;
    }

    if (is_perl_builtin_with_multiple_arguments($keyword))
    {
        $MULTI{$keyword} = 1;
    }

    if (is_perl_builtin_with_no_arguments($keyword))
    {
        $NONE{$keyword} = 1;
    }

    if (is_perl_builtin_with_one_argument($keyword))
    {
        $ONE{$keyword} = 1;
    }

    if (is_perl_builtin_with_optional_argument($keyword))
    {
        $OPT{$keyword} = 1;
    }
}

sub _escapeKeyword
{
    my ($keyword) = @_;

    # change $= -> $\=
    $keyword =~ s/(.*?)\=/$1\\=/;
    # change $: -> $\:
    $keyword =~ s/(.*?)\:/$1\\:/;
    # change $# -> $\#
    $keyword =~ s/(.*?)\#:/$1\\#/;

    return $keyword;
}

sub _genEmptyDoc
{
    return '';
}

sub _genPerlDoc
{
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

    #
    # take the first block of text before the double newline for display in
    # the popup annotations
    #
    $perldoc =~ s/(.*?\.)\n\n.*/$1/sm;

    # replace slashes and newlines for java
    $perldoc =~ s/\\/\\\\/g;
    $perldoc =~ s/\n/\\n/g;

    return ($perldoc) ? $perldoc : '';
}

sub _getOutputFileHandle
{
    my $file = sprintf '%s.%s', File::Spec->catfile(@_), SUFFIX;
    printf STDERR "creating bundle: %s\n", $file;

    return IO::File->new($file, 'w');
}

sub _matches_filter
{
    my ($keyword, $filters) = @_;

    return grep {$keyword =~ m/$_/} @{$filters};
}

sub _validateDir
{
    my ($dir) = @_;

    if (!$dir)
    {
        printf "usage: %s <path_to_bundle_location>\n", $0;
        exit(1);
    }

    if (!(-e $dir && -d $dir))
    {
        printf "invalid output directory: %s\n", $dir;
        exit(1);
    }
}

