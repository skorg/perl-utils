package PodToBundle;

use strict;
use warnings;

use fields qw(name dir);

use Hash::Util;
use IO::File;

use Pod::Find;
use Pod::POM;

use constant {
    SUFFIX => 'properties'
};

# fix for Pod::POM bug 61083
push @Pod::POM::Node::Begin::ACCEPT, qw(over item head1 head2 head3 head4);

sub new
{
    my $class = shift;
    my %args  = @_;
    Hash::Util::lock_keys(%args);
    
    my $self = fields::new($class);
    
    $self->{dir}  = $args{dir};
    $self->{name} = $args{name}; 

    return $self;
}

sub convert
{
    my $self = shift;
    
    my $idx = 0;
    my $pom = _parse_pod($self->_getPodName);
    
    my $file = _getOutputFileHandle($self->{dir}, $self->{name});
    
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
    my $last;
    
    # this should be the 'item' object that has the data we want 
    foreach my $root ($self->_getRoot($pom))
    {
        # figure out what type we are - error, warning, etc...
        my $type = $self->_getType($root);

        # convert each of the items        
        foreach my $item ($self->_getItems($root))
        {  
            _convert($self, $file, \$idx, \$last, $type, $item);
        }
    }
}

sub validateDir
{
    my $dir = shift->{dir};

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

## sub-class

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

#
# get the name of the pod file to convert
# 
sub _getPodName
{
    die 'bad monkey, implement me!';
}

#
# get the 'type'...error, warning, etc
#
sub _getType
{
    my $self = shift;
    my ($item) = @_;
    
    die 'bad monkey, implement me!';
}

#
# get the 'root' item node that contains the data we want 
#
sub _getRoot
{
    my $self = shift;
    my ($pom) = @_;
    
    die 'bad monkey, implement me!';
}

sub _nextItemHasContent
{
    return 0;
}

sub _prepContent
{
    my $self = shift;
    my ($text) = @_;

    # remove any leading and/or trailing newlines at the end of the document
    $text =~ s/^\n+//;
    $text =~ s/\n+$//g;

    # replace newlines w/ a single space
    $text =~ s/\n/ /g;
    # replace tabs w/ a single space
    $text =~ s/\t/ /g;
    # single space everything
    $text =~ s/\s{2,}/ /g;

    # replace slashes for java
    $text =~ s/\\/\\\\/g;

    return $text;
}

sub _prepTitle
{
    my $self = shift;
    my ($text) = @_;
    
    # replace any leading white space
    $text =~ s/^\s//g;

    # replace '(){}[]$?|*+' with '.'
    $text =~ s/([\(\)\{\}\[\]\$\?\|\*\+\\])/./g;

    # replace '%s %c %d %lx' with '.*?'
    $text =~ s/%([sdcl][x]{0,1})/.*?/g;

    # replace '%.[0-9]s' with '.*?'
    $text =~ s/%\.[0-9]s/.*?/g;

    return $text;    
}

## private

sub _convert
{
    my $self = shift;
    my ($file, $idx, $last, $type, $item) = @_;
    
    my $content = $item->content;

    if (!($content && $content ne ''))
    {
        if ($self->_nextItemHasContent)
        {               
            $$last = $item;
        }
        
        return;
    }
    
    my $mesg = $self->_prepTitle($item->title);        
    my $desc = $self->_prepContent($item->content);                    
            
    if ($$last)
    {
        my $prev = $self->_prepTitle($$last->title);                                    
        _write_entry($file, $$idx++, $type, $prev, $desc);
                
        $$last = undef;
    }
         
    _write_entry($file, $$idx++, $type, $mesg, $desc);
}

sub _find_pod_file
{
    my ($pod) = @_;
    
    my $file = Pod::Find::pod_where({-inc => 1}, $pod);
    if (!$file)
    {
        print STDERR "unable to find pod for '$pod', aborting...\n";
        exit(1);
    }

    return $file;
}

sub _getOutputFileHandle
{
    my $file = sprintf '%s.%s', File::Spec->catfile(@_), SUFFIX;
    printf STDERR "creating bundle: %s\n", $file;

    return IO::File->new($file, 'w');
}

sub _parse_pod
{
    my ($pod) = @_;
    
    my $file   = _find_pod_file($pod);
    my $parser = Pod::POM->new(warn => 0);

    my $parsed = $parser->parse_file($file);
    if (!$parsed)
    {
        printf STDERR "unable to '$pod' pod: %s\n", $parser->error;
        exit(1);
    }

    return $parsed;
}

sub _write_entry
{
    my $file = shift;
    my @args = @_;    
    
    # printf STDERR "%d=%s|%s|%s\n", @args;
    printf $file "%d=%s|%s|%s\n", @args;
}

1;