
# Intro

This guide is written from what I learned on my windows 7 machine.
It's about using using vagrant with the files in this folder to create and run a metasfresh-dev virtualbox instance.

Currently, that instance will contain metasfresh-admin and an ELK stack

When the instance runs, it can be accessed as follows:

* metasfresh-admin: http://localhost:9090
* kibana: http://localhost:5601
* logstash: tcp://localhost:5000
* ssh localhost:2222 (but also see the section about which shell to use)

# Local preparations

## Install virtualbox

You can download the installer from https://www.virtualbox.org/wiki/Downloads

## Install vagrant

You can download the installer from https://www.vagrantup.com/downloads.html

Avoid vagrant 1.9.4, because i "often" ran into this issue with it
https://github.com/mitchellh/vagrant/issues/8520

So either wait for vagrant 1.9.5 or install https://releases.hashicorp.com/vagrant/1.9.2/vagrant_1.9.2.msi (because 1.9.3 also might have some weird errors, but not sure)

Note that vagrant will probably ask you to restart your box.

# Which shell to use

You can run vagrant from the normal windows command line (I mean `cmd.exe`), but there the vagrant ssh command will probably not work (unless you explicitly configured this, which I didn't).

Instead of running vagrant from `cmd.exe` I run if from Git Bash (`git-bash.exe`) which starts a bash like environment including ssh. 

Git Bash comes with git, so you might already have it.