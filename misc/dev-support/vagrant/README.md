
# Intro

This guide is written from what I learned on my windows 7 machine.
It's about using using vagrant with the files in this folder to create and run a metasfresh-dev virtualbox instance.

Currently, that instance will contain metasfresh-admin, metasfresh-db and an ELK stack

When the instance runs, it can be accessed as follows:

* docker: http://localhost:4243
  * known to work with [eclipse docker tooling](https://wiki.eclipse.org/Linux_Tools_Project/Docker_Tooling/User_Guide)
* metasfresh-admin: http://localhost:9090
* metasfresh-db: localhost:5432
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

Note that vagrant will probably ask you to restart your box.

# Run it

once you have virtualbox and vagrant installed, you can
* clone this repo
* `cd`  into this folder (i.e. the `vagrant` folder)
* run `vagrant up`

This should cause vagrant to get all the neccessary stuff to provide you local virtual  metasfresh dev machine

To stop the machine, run `vagrant halt`

# Which shell to use

You can run vagrant from the normal windows command line (I mean `cmd.exe`), but there the vagrant ssh command will probably not work (unless you explicitly configured this, which I didn't).

Instead of running vagrant from `cmd.exe` I run if from Git Bash (`git-bash.exe`) which starts a bash like environment including ssh.

Git Bash comes with git, so you might already have it.

# Eclipse docker tooling

If you have this virtual machine running locally, and if you installed [eclipse docker tooling](https://wiki.eclipse.org/Linux_Tools_Project/Docker_Tooling/User_Guide), then  you can connect to the docker deamon from eclipse like this:

<img src="/vagrant/images/eclipse_docker_tooling_connect.png" width="33%" alt="eclipse docker tooling connect dialog"></img>
