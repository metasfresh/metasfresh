#!/bin/bash

# The variables have defaults that can be set from outside, e.g. via -e "DB_HOST=mydbms" or from the docker-compose.yml file.
# Also see https://docs.docker.com/engine/reference/builder/#environment-replacement
webapi_protocol=${WEBUI_API_PROTOCOL:-https}
webapi_host=${WEBUI_API_HOST:-webapi}
webapi_port=${WEBUI_API_PORT:-8080}

msv3_protocol=${MSV3_API_PROTOCOL:-https}
msv3_host=${MSV3_API_HOST:-webapi}
msv3_port=${MSV3_API_PORT:-8080}

config_js_file='/usr/share/nginx/html/config.js'
sed -Ei "s/WEBUI_API_PROTOCOL/${webapi_protocol}/g" $config_js_file
sed -Ei "s/WEBUI_API_HOST/${webapi_host}/g" $config_js_file
sed -Ei "s/WEBUI_API_PORT/${webapi_port}/g" $config_js_file

config_nginx_file='/etc/nginx/conf.d/default.conf'
sed -Ei "s/WEBUI_API_PROTOCOL/${webapi_protocol}/g" $config_nginx_file
sed -Ei "s/WEBUI_API_HOST/${webapi_host}/g" $config_nginx_file
sed -Ei "s/WEBUI_API_PORT/${webapi_port}/g" $config_nginx_file
sed -Ei "s/MSV3_API_PROTOCOL/${msv3_protocol}/g" $config_nginx_file
sed -Ei "s/MSV3_API_HOST/${msv3_host}/g" $config_nginx_file
sed -Ei "s/MSV3_API_PORT/${msv3_port}/g" $config_nginx_file

echo "WEBUI_API_PROTOCOL=$webapi_protocol"
echo "WEBUI_API_HOST=$webapi_host"
echo "WEBUI_API_PORT=$webapi_port"

echo "MSV3_API_PROTOCOL=$msv3_protocol"
echo "MSV3_API_HOST=$msv3_host"
echo "MSV3_API_PORT=$msv3_port"

echo "Starting nginx -g 'daemon off;'"

nginx -g 'daemon off;'
