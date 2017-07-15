#!/bin/bash
#
# This script outputs a config.js file such that the webui-frontend connects to the API-server set in $API_URL
# see https://github.com/metasfresh/metasfresh-webui-frontend/issues/1013#issuecomment-314999729
#

echo "const config =  {"
echo "    API_URL: '$API_URL/rest/api',"
echo "    WS_URL:  '$API_URL/stomp'"
echo "}"

