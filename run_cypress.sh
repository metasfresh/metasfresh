#!/bin/bash

frontend_url=${FRONTEND_URL:-http://localhost:3000}
api_url=${API_URL:-http://localhost:8080/rest/api}
plugin_api_url=${PLUGIN_API_URL:-http://localhost:9192/}
ws_url=${WS_URL:-http://localhost:8080/stomp}
username=${USERNAME:-metasfresh}
password=${PASSWORD:-metasfresh}
cypress_record_key=${RECORD_KEY:-NOT_SET}

echo "*************************************************************"
echo "Display the variable values we run with"
echo "*************************************************************"
echo ""
echo "FRONTEND_URL=$frontend_url"
echo "API_URL=$api_url"
echo "PLUGIN_API_URL=$plugin_api_url"
echo "WS_URL=$ws_url"
echo "USERNAME=$username"
echo "PASSWORD=***"

if [[ $cypress_record_key = "NOT_SET" ]]
then
    echo "RECORD_KEY is not set"
else
    echo "RECORD_KEY=***"
fi
cd /e2e

# write the config file used by cypress to acces the rest-API etc
echo "module.exports = {" > cypress/config.js
echo "  API_URL: '${api_url}'," >> cypress/config.js
echo "  PLUGIN_API_URL: '${plugin_api_url}'," >> cypress/config.js
echo "  WS_URL: '${ws_url}'," >> cypress/config.js
echo "  username: '${username}'," >> cypress/config.js
echo "  password: '${password}'," >> cypress/config.js
echo "};" >> cypress/config.js

if [[ $cypress_record_key = "NOT_SET" ]]
then
    record_param=""
else
    record_param="--record --key $cypress_record_key"
fi

CYPRESS_baseUrl=$frontend_url node_modules/.bin/cypress run $record_param || sleep 1h #if the cypress command fails, keep running so that we can inspect the container
