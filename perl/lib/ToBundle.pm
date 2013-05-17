package ToBundle;
use strict;
use warnings;

use fields qw(fh writer);

use Data::Dumper;

use File::Spec qw();
use Hash::Util qw();

use IO::File;

use Pod::POM;

use XML::Writer;

# fix for Pod::POM bug 61083
push @Pod::POM::Node::Begin::ACCEPT, qw(over item head1 head2 head3 head4);

use constant {
    BASE   => File::Spec->catdir('..', qw(src org scriptkitty perl)),
    SUFFIX => 'xml'
};

sub new
{
    my $class = shift;
    my %args  = @_;
    Hash::Util::lock_keys(%args);

    my $self = fields::new($class);

    $self->{writer} = XML::Writer->new(
        DATA_INDENT => 2,
        DATA_MODE   => 1,
        OUTPUT      => _getOutputFileHandle($self),
    );

    return $self;
}

sub convert
{
    my $self = shift;

    $self->{writer}->startTag($self->_getXmlRootTag);

    foreach my $data (@{$self->_getToConvert})
    {
        if (exists $data->{pom})
        {
            _podEntry($self, $data);
        }
        else
        {
            _convert($self, [$data->{keyword}], '', $data);
        }
    }

    $self->{writer}->endTag;
}

## sub-class

sub _getBundleName
{
    die 'bad monkey, implement me!';
}

sub _getDestDir
{
    die 'bad monkey, implement me!';
}

#
# get the actual content items - in some cases, this may just be $root' 
# itself, other times further traversal will be required
#
sub _getItems
{
    my $self = shift;
    my ($root) = @_;
    
    die 'bad monkey, implement me!';
}

sub _getRoot
{
    my $self = shift;
    my ($pom) = @_;
    
    die 'bad monkey, implement me!';
}

sub _getToConvert
{
    die 'bad moneky, implement me!';
}

sub _getType
{
    my $self = shift;
    my ($root) = @_;

    return;
}

#
# returns then name of the root xml element
#

sub _getXmlRootTag
{
    die 'bad moneky, implement me!';
}

#
# returns then name of the 'grouping' xml element
#
sub _getXmlElementTag
{
    die 'bad moneky, implement me!';
}

sub _parsePod
{
    my $self = shift;
    my ($toParse) = @_;

    my $parser = Pod::POM->new(warn => 0);
    my $parsed = $parser->parse($toParse);

    if (!$parsed)
    {
        printf STDERR "unable to pod: %s\n", $parser->error;
        exit(1);
    }

    return $parsed;
}

sub _prepContent
{
    my $self = shift;
    my ($text) = @_;
    
    # escape slashes
#    $text =~ s|\\|\\\\|g;
    # escape newlines   
#    $text =~ s|\r*\n|\\n|g;
    
    # replace any double spaces w/ a single space
    $text =~ s|\.\s{2,}|. |g;

    return $text;
}

sub _prepTitle
{
    die 'bad moneky, implement me!';
}

sub _writeTitles
{
    die 'bad moneky, implement me!';
}

## private

sub _convert
{
    my $self = shift;
    my ($titles, $content, $data) = @_;

    $self->{writer}->startTag($self->_getXmlElementTag);

    if (exists $data->{type})
    {
        $self->{writer}->dataElement('type', $data->{type});
    }
    
    if (exists $data->{keyword})
    {
        $self->{writer}->cdataElement('name', $data->{keyword});
    }

    $self->_writeTitles($self->{writer}, $titles);
    
    if (!_isEmpty($content))
    {
        $self->{writer}->cdataElement('content', "$content");
    }

    $self->{writer}->endTag;
}

sub _getOutputFileHandle
{
    my $self = shift;

    my $dest = File::Spec->catfile(BASE, $self->_getDestDir);
    if (!-d $dest)
    {
        die "bundle destination [$dest] does not exist";
    }

    my $name = $self->_getBundleName;
    my $file = sprintf '%s.%s', File::Spec->catfile($dest, $name), SUFFIX;

    printf STDERR "creating bundle: %s\n", $file;

    return IO::File->new($file, 'w');
}

sub _isEmpty
{
    return ($_[0] && $_[0] ne '') ? 0 : 1;
}

sub _podEntry
{
    my $self = shift;
    my ($data) = @_;

    my $pom = $data->{pom};
    delete $data->{pom};

    # this should be the 'item' object that has the data we want
    foreach my $root ($self->_getRoot($pom))
    {
        # figure out what type we are - error, warning, etc...
        if (my ($type) = $self->_getType($root, $data))
        {
            $data->{type} = $type;
        }

        #
        # meh...some pod documents allow for this:
        #
        #   =item A
        #   =item B
        #     here's my content
        #   =cut
        #
        # so store last 'item' seen so we can pull it's title on the
        # next go around...
        #
        my @titles = ();
        my $content;

        # convert each of the items
        foreach my $item ($self->_getItems($root))
        {
            push @titles, $self->_prepTitle($item->title);
            
            $content = $item->content;
            if (_isEmpty($content))
            {
                next;
            }
        }
               
        $content = $self->_prepContent($content);
        _convert($self, \@titles, $content, $data);
    }
}

1;
