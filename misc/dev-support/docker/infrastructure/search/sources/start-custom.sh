#!/bin/sh

# Give the folder to the docker image's elasticsearch user.
# This is needed so that we can mount it on the dockerhost.
# Also see https://discuss.elastic.co/t/elastic-elasticsearch-docker-not-assigning-permissions-to-data-directory-on-run/65812/2
/usr/bin/chown 1000:0 /usr/share/elasticsearch/data

# now call the actual entry point script that is in the original Dockerfile's ENTRYPOINT
/usr/local/bin/docker-entrypoint.sh
