#!/bin/bash
#
# This short script calls config_from_env.sh to write the config.js with the API_URL take from the environment and then starts the nodejs server.
# See the Dockerfile for further infos.
#

/usr/src/app/config_from_env.sh > /usr/src/app/config.js && \
echo "We will run with the following config.js file:" && \
cat /usr/src/app/config.js && \
npm start
