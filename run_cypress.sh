#!/bin/bash

frontend_url=${FRONTEND_URL:-http://localhost:3000}
api_url=${API_URL:-http://localhost:8080/rest/api}
plugin_api_url=${PLUGIN_API_URL:-http://localhost:9192/}
ws_url=${WS_URL:-http://localhost:8080/stomp}
username=${USERNAME:-metasfresh}
password=${PASSWORD:-metasfresh}
cypress_record_key=${RECORD_KEY:-NOT_SET}
no_exit=${NO_EXIT:-NOT_SET}
debug=${DEBUG:-NOT_SET}

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
    record_param=""
else
    echo "RECORD_KEY=***"
    record_param="--record --key $cypress_record_key"
fi
if [[ no_exit = "NOT_SET" ]]
then
    echo "NO_EXIT is not set"
    noexit_param=""
else
    echo "NO_EXIT is set (=$no_exit)"
    noexit_param="--no-exit"
fi
if [[ debug = "NOT_SET" ]]
then
    echo "DEBUG is not set"
    debug_param=""
else
    echo "DEBUG is set (=$debug)"
    debug_param="DEBUG=cypress:*"
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

$debug_param CYPRESS_baseUrl=$frontend_url node_modules/.bin/cypress run $record_param $noexit_param
