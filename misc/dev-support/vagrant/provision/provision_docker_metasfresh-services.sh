#!/usr/bin/env bash

# Important: if you fiddle with this file on windows, please make sure that the line endings are for linux!

# Parameters:
# -d, --detach			Run container in background and print container ID
# -p, --publish list	Publish a container's port(s) to the host (default [])
# -t, --tty 			Allocate a pseudo-TTY..why here?
# --name string			Assign a name to the container

echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Providing & starting metasfresh-admin on port 9090"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
docker run --name metasfresh-admin -d -p 9090:9090 -t metasfresh/metasfresh-admin:master-latest
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Done providing & starting metasfresh-admin on port 9091"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Providing & starting metasfresh-db on port 5432"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
docker run --name metasfresh-db -d -p 5432:5432 -t metasfresh/metasfresh-db:latest
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Done providing & starting metasfresh-db on port 5432"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
