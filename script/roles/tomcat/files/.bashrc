# .bashrc

# Source global definitions
if [ -f /etc/bashrc ]; then
        . /etc/bashrc
fi

# User specific aliases and functions

MVN_HOME=/home/ec2-user/tools/apache-maven-3.1.1
PATH=$PATH:$MVN_HOME/bin
