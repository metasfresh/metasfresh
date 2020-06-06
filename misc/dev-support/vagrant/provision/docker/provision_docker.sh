#!/usr/bin/env bash

# Important: if you fiddle with this file on windows, please make sure that the line endings are for linux!

echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Preparing the additional disk /dev/sdc for docker"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
# Add the additional storage we provided in our Vagrantfile
# Goal: make it available for docker
# It's sdc because the image ubuntu/xenial64 that we use already created sda and sdb
pvcreate /dev/sdc
vgcreate storage /dev/sdc
lvcreate -l 100%FREE -n docker storage
mkfs.ext4 /dev/storage/docker

# We create /var/lib/docker and mount /dev/storage/docker into it before docker is installed
# If docker complains about the already- existing folder, try commenting this out and add the following *after* the "apt install docker.." part
#systemctl stop docker.service
#mount /dev/storage/docker /mnt
#rsync -rav /var/lib/docker/ /mnt/
#umount /mnt
#mount /dev/storage/docker /var/lib/docker
#systemctl start docker.service
mkdir -p /var/lib/docker

echo '' >> /etc/fstab
echo '# the following like was added by the script provision_docker.sh' >> /etc/fstab
echo '/dev/storage/docker  /var/lib/docker              ext4     defaults                              0       2' >> /etc/fstab

mount -v /dev/storage/docker


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Installing docker"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
# Install docker
# thanks to https://docs.docker.com/engine/installation/linux/ubuntu/
apt install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    software-properties-common
	
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
   
apt update

# installing 17.03.1~ce-0~ubuntu-xenial because it is the latest version right now
# installing a fixed version, because we also install docker-compose, and there is seems as if we *have* to specify a version
# so in installing a fixed version here, we make sure that the docker-ce version and the docker-compose version are compatible
apt install -y docker-ce=17.03.1~ce-0~ubuntu-xenial

echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Installing docker-compose"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
curl -L https://github.com/docker/compose/releases/download/1.13.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose

# Allow ubuntu to run docker commands without prepending "sudo"
# thanks to https://askubuntu.com/a/739861
usermod -aG docker ubuntu

echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Enabling docker remote API on port 4243"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
# enable docker to run with the tcp remote API
# thx to https://stackoverflow.com/documentation/docker/3935/docker-engine-api#t=201707141850020254931
cp -v /vagrant/provision/docker/docker-tcp.socket /etc/systemd/system/docker-tcp.socket
systemctl enable docker-tcp.socket
systemctl enable docker.socket
systemctl stop docker
systemctl start docker-tcp.socket
systemctl start docker


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Done installing docker and docker-compose"
#echo "Done installing docker, docker-compose and docker-machine"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"