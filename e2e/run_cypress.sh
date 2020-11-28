#!/bin/bash

# this script is intended to be the entry point for the docker startup script

frontend_url=${FRONTEND_URL:-http://localhost:3000}
api_url=${API_URL:-http://localhost:8080/rest/api}
plugin_api_url=${PLUGIN_API_URL:-http://localhost:9192/}
ws_url=${WS_URL:-http://localhost:8080/stomp}
username=${USERNAME:-metasfresh}
password=${PASSWORD:-metasfresh}
cypress_spec=${CYPRESS_SPEC:-NOT_SET}
cypress_record_key=${CYPRESS_RECORD_KEY:-NOT_SET}
cypress_browser=${CYPRESS_BROWSER:-electron}
debug_cypress_output=${DEBUG_CYPRESS_OUTPUT:-n}
debug_print_bash_cmds=${DEBUG_PRINT_BASH_CMDS:-n}
debug_sleep_after_fail=${DEBUG_SLEEP_AFTER_FAIL:-n}

echo "*************************************************************"
echo "Display the variable values we run with"
echo "*************************************************************"
echo ""
echo "CYPRESS_BROWSER=$cypress_browser"
echo "CYPRESS_SPEC=$cypress_spec"
echo "FRONTEND_URL=$frontend_url"
echo "API_URL=$api_url"
echo "PLUGIN_API_URL=$plugin_api_url"
echo "WS_URL=$ws_url"
echo "USERNAME=$username"
echo "PASSWORD=***"

if [ "$cypress_record_key" = "NOT_SET" ]
then
    echo "RECORD_KEY is not set"
    record_param=""
else
    echo "RECORD_KEY=***"
    record_param="--record --key $cypress_record_key"
fi

if [ "$cypress_spec" = "NOT_SET" ]
then
    echo "CYPRESS_SPEC is not set; running all specs"
    spec_param=""
else
    spec_param="--spec $cypress_spec"
fi

echo "DEBUG_SLEEP_AFTER_FAIL=$debug_sleep_after_fail"

echo "DEBUG_CYPRESS_OUTPUT=$debug_cypress_output"
if [ "$debug_cypress_output" != "n" ];
then
    echo "DEBUG_CYPRESS_OUTPUT=${debug_cypress_output}, so we export DEBUG=cypress:* as env variable; set to n (just the lowercase letter) to skip this."
    export DEBUG=cypress:*
fi

# start printing all bash commands from here onwards, if activated
if [ "$debug_print_bash_cmds" != "n" ];
then
	echo "DEBUG_PRINT_BASH_CMDS=${debug_print_bash_cmds}, so from here we will output all bash commands; set to n (just the lowercase letter) to skip this."
	echo ""
    set -x
fi

# write the config file used by cypress to acces the rest-API etc
echo "module.exports = {" > /e2e/cypress/config.js
echo "  API_URL: '${api_url}'," >> /e2e/cypress/config.js
echo "  PLUGIN_API_URL: '${plugin_api_url}'," >> /e2e/cypress/config.js
echo "  WS_URL: '${ws_url}'," >> /e2e/cypress/config.js
echo "  username: '${username}'," >> /e2e/cypress/config.js
echo "  password: '${password}'," >> /e2e/cypress/config.js
echo "};" >> /e2e/cypress/config.js

cd /e2e

export CYPRESS_baseUrl=$frontend_url  

# Thx to https://docs.cypress.io/guides/tooling/reporters.html#Spec-to-STDOUT-save-JUnit-XML-files
reporter_param="--reporter mocha-multi-reporters --reporter-options configFile=reporter-config.json"

# note: run with chrome after running with electron hung on jenkins; Probably related to https://github.com/cypress-io/cypress/issues/1912
# ofc this assumes that the docker image contains chrome..
# also note, that unless running with electron, we can't record videos
node_modules/.bin/cypress run $reporter_param $record_param $spec_param $noexit_param --browser $cypress_browser

cypress_exit_status=$?
echo "cypress_exit_status=$cypress_exit_status"

if [ "$cypress_exit_status" != "0" -a  "$debug_sleep_after_fail" != "n" ];
then
    echo "DEBUG_SLEEP_AFTER_FAIL=$debug_sleep_after_fail and cypress exited with status=$cypress_exit_status, so we sleep 1h now; set to n (just the lowercase letter) to skip this."
    sleep 1h
fi
