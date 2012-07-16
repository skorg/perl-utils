package ToBundle;
use strict;
use warnings;

use fields qw(fh idx);

use File::Spec qw();
use Hash::Util qw();

use IO::File;

use Pod::POM;

# fix for Pod::POM bug 61083
push @Pod::POM::Node::Begin::ACCEPT, qw(over item head1 head2 head3 head4);

use constant {
    BASE   => File::Spec->catdir('..', qw(src org scriptkitty perl)),
    SUFFIX => 'properties'
};

sub new
{
    my $class = shift;
    my %args  = @_;
    Hash::Util::lock_keys(%args);

    my $self = fields::new($class);

    $self->{idx} = 0;
    $self->{fh}  = _getOutputFileHandle($self);

    return $self;
}

sub convert
{
    my $self = shift;

    foreach my $data (@{$self->_getToConvert})
    {
        if (exists $data->{pom})
        {
            _podEntry($self, $data);            
        }
        else
        {
            _emptyEntry($self, $data);
        }               
    }
}

## sub-class

sub _combineTitles
{
    return 0;
}

sub _getBundleName
{
    die 'bad monkey, implement me!';
}

sub _getDestDir
{
    die 'bad monkey, implement me!';
}

sub _getEmptyKey
{
    die 'bad monkey, implement me!';
}

sub _getEmptyValue
{
    my $self = shift;
    my ($data) = @_;
    
    return '';
}

sub _getPodKey
{
    die 'bad monkey, implement me!'; 
}

sub _getPodValue
{
    my $self = shift;
    my ($title, $content, $data) = @_;

    my $value = sprintf '%s|%s', $title, $content; 
    
    if (exists $data->{type})
    {
        $value = sprintf '%s|%s', $data->{type}, $value;
    }

    return $value;  
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

sub _idx
{
    return shift->{idx}++;
}

sub _nextItemHasContent
{
    return 0;
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
    die 'bad moneky, implement me!';
}

sub _prepTitle
{
    die 'bad moneky, implement me!';
}

## private

sub _convert
{
    my $self = shift;
    my ($list, $item, $data) = @_;

    my $file    = $self->{fh};
    my $content = $item->content;

    push @{$list}, $self->_prepTitle($item->title);

    if (!($content && $content ne ''))
    {
        if (!$self->_nextItemHasContent)
        {
            pop @{$list};
        }

        return;
    }

    $content = $self->_prepContent($content);

    if ($self->_combineTitles)
    {
        $list = [join '\n', @{$list}];
    }

    foreach my $title (@{$list})
    {
        my $key   = $self->_getPodKey($data);
        my $value = $self->_getPodValue($title, $content, $data);
        
        _write($self, $key, $value);
    }

    @{$list} = ();
}

sub _emptyEntry
{
    my $self = shift;
    my ($data) = @_;
    
    my $key   = $self->_getEmptyKey($data);
    my $value = $self->_getEmptyValue($data);
        
    _write($self, $key, $value);
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

sub _podEntry
{
    my $self = shift;
    my ($data) = @_;

    my $pom = $data->{pom};
    delete $data->{pom};

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
    my @last = ();

    # this should be the 'item' object that has the data we want
    foreach my $root ($self->_getRoot($pom))
    {
        # figure out what type we are - error, warning, etc...
        if (my ($type) = $self->_getType($root, $data))
        {
            $data->{type} = $type;
        }

        # convert each of the items
        foreach my $item ($self->_getItems($root))
        {
            _convert($self, \@last, $item, $data);
        }
    }
}

sub _write
{
    my $self = shift;
    my @args = @_;
    
    my $file = $self->{fh};
    
    printf $file "%s=%s\n", @args;
}

1;
