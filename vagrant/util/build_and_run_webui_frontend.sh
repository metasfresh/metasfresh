#!/bin/bash
set -e

# this script will be in /vagrant/util in the running ubuntu machine
# Important: if you fiddle with this file on windows, please make sure that the line endings are for linux!

if [ ! -d ~/metasfresh-webui-frontend ]; then
	echo "clone the latest metasfresh-webui-frontend"
	cd ~
	git clone https://github.com/metasfresh/metasfresh-webui-frontend.git
	cd ./metasfresh-webui-frontend
else
	echo "pull the latest metasfresh-webui-frontend"
	cd ~/metasfresh-webui-frontend
	git pull
fi

echo "docker stop container (if it runs)"
docker stop metasfresh-webui-frontend || true
#docker rm metasfresh-webui-frontend not needed because we run it with --rm

echo "docker build"
docker build --file ./docker/Dockerfile-dev --tag metasfresh-webui-frontend-dev .

echo "docker run, connect to webui-api on IP 192.168.1.81"
# --rm Automatically remove the container when it exits
docker run --rm --name metasfresh-webui-frontend -p 3000:3000 -e "API_URL=http://192.168.1.81:8080" -d metasfresh-webui-frontend-dev