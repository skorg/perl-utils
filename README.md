perl-utils 
==========

[![Build Status](https://scriptkitty.ci.cloudbees.com/buildStatus/icon?job=perl-utils)](https://scriptkitty.ci.cloudbees.com/job/perl-utils/)

set of perl language utilities - originially part of the [perlipse](https://github.com/skorg/perlipse) project

#### eclipse import

this project is built using a mixture of tycho and regular maven jar features. in order to import this project into eclipse, all of the
project's dependencies must be available as plugins in eclipse - how you choose to achieve this is up to you. 

once installed, the project can be imported as an existing maven project, however, a side effect of using this mixed maven structure means 
the test sources need to be manually added to the build path.

[![pbc](http://www.cloudbees.com/sites/default/files/Button-Built-on-CB-1.png)](http://www.cloudbees.com)