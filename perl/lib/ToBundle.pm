package ToBundle;

use strict;
use warnings;

use fields qw(fh);

use File::Spec qw();
use Hash::Util qw();

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
        
    $self->{fh} = _getOutputFileHandle($args{dir}, $args{name});
    
    return $self;        
}

## sub-class

sub _escapeNewline
{
    my $self = shift;
    my ($text) = @_;
    
    $text =~ s/\n/\\n/g;
    
    return $text;
}

sub _escapeSlashes
{
    my $self = shift;
    my ($text) = @_;
    
    $text =~ s/\\/\\\\/g;

    return $text;    
}

sub _prepContent
{
    my $self = shift;
    my ($text) = @_;

    $text = $self->_trim($text);    
    $text = $self->_escapeSlashes($text);
    $text = $self->_escapeNewline($text);

    return $text;
}

sub _singleSpace
{
    my $self = shift;
    my ($text) = @_;
    
    $text =~ s/\t/ /g;
    $text =~ s/\s{2,}/ /g;
        
    return $text;
}

sub _trim
{
    my $self = shift;
    my ($text) = @_;
    
    $text =~ s/^\s+//g;
    $text =~ s/\s+$//g; 
    
    $text =~ s/^\n+//;
    $text =~ s/\n+$//g;
    
    return $text;
}

## private

sub _getOutputFileHandle
{
    my ($dir, $name) = @_;
    
    my $dest = File::Spec->catfile(BASE, $dir);
    if (!-d $dest)
    {
        die "bundle destination [$dest] does not exist";
    }
    
    my $file = sprintf '%s.%s', File::Spec->catfile($dest, $name), SUFFIX;
    printf STDERR "creating bundle: %s\n", $file;

    return IO::File->new($file, 'w');
}

1;