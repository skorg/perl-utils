package ToBundle;
use strict;
use warnings;

use fields qw(fh writer);

use Logger;

use Data::Dumper;
use File::Spec qw();
use Hash::Util qw();
use IO::File;
use Pod::POM;
use Tie::Hash::Indexed;
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
        $self->_convert($data);
    }

    $self->{writer}->endTag;
}

## sub-class

sub _addData
{
    my $self = shift;
    my ($root, $node, $data) = @_;

    die 'bad monkey, implement me!';
}

sub _convert
{
    my $self = shift;
    my ($data) = @_;
    
    foreach my $root ($self->_getRoot(delete $data->{pom}))
    {
        foreach my $node ($self->_getNodes($root))
        {
            $self->_addData($root, $node, $data);          
            $self->_write($node, $data);          
        }
    }
}

sub _getBundleName
{
    die 'bad monkey, implement me!';
}

sub _getEmptyDataHash
{
    tie my %hash, 'Tie::Hash::Indexed';
    return \%hash;
}

sub _getDestDir
{
    die 'bad monkey, implement me!';
}

sub _getNodes
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

sub _isEmpty
{
    my $self = shift;
    my ($str) = @_;

    return ($str && $str ne '') ? 0 : 1;
}

sub _parsePod
{
    my $self = shift;
    my ($toParse) = @_;

    my $parser = Pod::POM->new(warn => 0);
    my $parsed = $parser->parse($toParse);

    if (!$parsed)
    {
        Logger::error("unable to pod: %s", $parser->error);
        exit(1);
    }

    return $parsed;
}

sub _prepContent
{
    my $self = shift;
    my ($content) = @_;
    
    return $content;
}

sub _strip_ws_and_nl
{
    my $self = shift;
    my ($text) = @_;
    
    # trim trailing/leading whitespace
    $text =~ s|^\s+||g;
    $text =~ s|\s+$||g;

    # trim leading/trailing newlines
    $text =~ s|^\r*\n||;
    $text =~ s|\r*\n$||;

    # replace any double spaces w/ a single space
    # $text =~ s|\.[^\S\n]{2,}|\. |g;

    return $text;    
}

sub _writeBundleSpecificElements
{
    my $self = shift;
    my ($writer, $data) = @_;
    
    foreach my $key (keys %{$data})
    {
        if ($key eq 'pattern')
        {
            $writer->cdataElement($key, $data->{$key});
        }
        else
        {
            $writer->dataElement($key, $data->{$key});
        }
    }
}

## private

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

    Logger::info("creating bundle: %s", $file);

    return IO::File->new($file, 'w');
}

sub _write
{
    my $self = shift;
    my ($node, $data) = @_;
    
    $self->{writer}->startTag($self->_getXmlElementTag);    

    Logger::debug("bundle specific data %s", sub {Dumper($data)}); 
    $self->_writeBundleSpecificElements($self->{writer}, $data);

    if (!$self->_isEmpty($node))
    {
        #
        # all sub-class implementations of '_prepContent' are disabled
        #
        my $content = $self->_prepContent($node);
        $self->{writer}->cdataElement('content', $node);
    }

    $self->{writer}->endTag;
}

1;
