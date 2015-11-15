# UrlRegWatch

## Short Description
Check internet pages for changes by regular expression

## Motivation
This program was motivated by the task to find changes on specific 
internet pages. Only in case of changes I want to be notified.

## Download and Installation
Binary JAR file can be downloaded from sourceforge:
https://sourceforge.net/projects/urlregwatch/files/

Arch Linux users should install the AUR package
https://aur.archlinux.org/packages/urlregwatch/

## Usage
Create a sub directory named .urlregwatch in your home directory 
including a file named urlregs.txt with URLs of internet pages followed
by a regular expression and an expected part of it. Here is an example 
file ~/.urlregwatch/urlregs.txt

~~~
https://github.com/OpenHelios/urlregwatch
(2) commits
2
~~~

More URLs followed by a regular expression and an expectation can be added.
Empty lines or lines beginning with the char # are ignored.

UrlRegWatch looks at these internet pages, search in the content for the 
regular expression and compares the result with the expected part.
The user will be notified and the exit code is different from 0, if
* the internet page could not be loaded,
* there is no hit with the regular expression searching ,
* the expected part could not be found in the result of the regular 
expression.

Start checking the URLs by opening a command line window and executing
~~~
java -jar urlregwatch-0.1.0.jar 
~~~
Creating a batch file or shell script called urlregwatch will make the
execution easier, i.e. executing
~~~
urlregwatch
~~~
can be configured for starting. 

Download and install Java from http://java.com, if the program java could not
be found on your machine. The result looks like this in case of no changes:
~~~
OK: https://github.com/OpenHelios/urlregwatch
~~~
Or in case of changes:
~~~
https://github.com/OpenHelios/urlregwatch:
    Expression "(2) commits" has not been found in content.
~~~
## Build
The easiest way is to clone the source repository and build it with gradle:
~~~
git clone https://github.com/OpenHelios/urlregwatch.git
gradle build
~~~

## History
* 0.1.2 download all URLs asynchrone to improve performance
* 0.1.1 minor changes in ouput format
* 0.1.0 initial release
