#!/usr/bin/env bash

# Important: if you fiddle with this file on windows, please make sure that the line endings are for linux!


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Providing & starting docker-elk"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

DOCKER_ELK_DIR=~ubuntu/docker-elk

if [ ! -d "$DOCKER_ELK_DIR" ]; then
	echo "Directory $DOCKER_ELK_DIR does not exist; cloning github repo"
	git clone https://github.com/deviantony/docker-elk.git
else
	echo "Directory $DOCKER_ELK_DIR exists; not cloning into it"
fi

cp -v /vagrant/provision/elk/logstash.conf $DOCKER_ELK_DIR/logstash/pipeline

cd $DOCKER_ELK_DIR
sudo docker-compose up -d

echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Done Providing & starting docker-elk"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
