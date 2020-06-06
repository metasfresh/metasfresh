#!/bin/bash
set -e

# this script will be in /vagrant/util in the running ubuntu machine
# Important: if you fiddle with this file on windows, please make sure that the line endings are for linux!

echo "docker stop container (if it runs)"
sudo docker stop metasfresh-db || true
sudo docker rm metasfresh-db

echo "docker pull"
sudo docker pull metasfresh/metasfresh-db

echo "docker run"
# --rm Automatically remove the container when it exits
sudo docker run --name metasfresh-db -p 5432:5432 -d metasfresh/metasfresh-db

echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo " IMPORTANT: REMEMBER TO CONFIGURE THE DB FOR DEVELOPMENT !"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
