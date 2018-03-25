#!/bin/bash

# The variables have defaults that can be set from outside, e.g. via -e "DB_HOST=mydbms" or from the docker-compose.yml file.
# Also see https://docs.docker.com/engine/reference/builder/#environment-replacement
webapi_host=${WEBUI_API_HOST:-webapi}
webapi_port=${WEBUI_API_PORT:-8080}

config_js_file='/usr/share/nginx/html/config.js'
sed -Ei "s/WEBUI_API_HOST/${webapi_host}/g" $config_js_file
sed -Ei "s/WEBUI_API_PORT/${webapi_port}/g" $config_js_file

nginx -g 'daemon off;'
