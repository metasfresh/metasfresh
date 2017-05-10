#!/usr/bin/env bash

# Important: if you fiddle with this file on windows, please make sure that the line endings are for linux!


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Providing & starting metasfresh-admin"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

docker run -d -p 9090:9090 -t metasfresh/metasfresh-admin:gh1471-metasfresh-latest

echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Done providing & starting metasfresh-admin"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
