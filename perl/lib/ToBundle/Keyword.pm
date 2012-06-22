package ToBundle::Keyword;
use base qw(ToBundle);

use strict;
use warnings;

use fields qw(name dir);

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

1;