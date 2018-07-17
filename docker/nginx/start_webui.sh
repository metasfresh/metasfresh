#!/bin/bash

# The variables have defaults that can be set from outside, e.g. via -e "DB_HOST=mydbms" or from the docker-compose.yml file.
# Also see https://docs.docker.com/engine/reference/builder/#environment-replacement
webapi_client_protocol=${WEBUI_API_CLIENT_PROTOCOL:-https}
webapi_client_host=${WEBUI_API_CLIENT_HOST:-webapi}
webapi_client_port=${WEBUI_API_CLIENT_PORT:-8080}

webapi_proxypass_protocol=${WEBUI_API_PROXYPASS_PROTOCOL:-https}
webapi_proxypass_host=${WEBUI_API_PROXYPASS_HOST:-webapi}
webapi_proxypass_port=${WEBUI_API_PROXYPASS_PORT:-8080}

msv3_proxypass_protocol=${MSV3_API_PROXYPASS_PROTOCOL:-https}
msv3_proxypass_host=${MSV3_API_PROXYPASS_HOST:-msv3}
msv3_proxypass_port=${MSV3_API_PROXYPASS_PORT:-8080}

debug_cat_nginx_conf_file=${DEBUG_CAT_NGINX_CONF_FILE:-n}

config_js_file='/usr/share/nginx/html/config.js'
sed -Ei "s/WEBUI_API_CLIENT_PROTOCOL/${webapi_client_protocol}/g" $config_js_file
sed -Ei "s/WEBUI_API_CLIENT_HOST/${webapi_client_host}/g" $config_js_file
sed -Ei "s/WEBUI_API_CLIENT_PORT/${webapi_client_port}/g" $config_js_file

config_nginx_file='/etc/nginx/conf.d/default.conf'
sed -Ei "s/WEBUI_API_PROXYPASS_PROTOCOL/${webapi_proxypass_protocol}/g" $config_nginx_file
sed -Ei "s/WEBUI_API_PROXYPASS_HOST/${webapi_proxypass_host}/g" $config_nginx_file
sed -Ei "s/WEBUI_API_PROXYPASS_PORT/${webapi_proxypass_port}/g" $config_nginx_file

sed -Ei "s/MSV3_API_PROXYPASS_PROTOCOL/${msv3_proxypass_protocol}/g" $config_nginx_file
sed -Ei "s/MSV3_API_PROXYPASS_HOST/${msv3_proxypass_host}/g" $config_nginx_file
sed -Ei "s/MSV3_API_PROXYPASS_PORT/${msv3_proxypass_port}/g" $config_nginx_file

echo "*************************************************************"
echo "Display the variable values we run with"
echo "*************************************************************"
echo ""
echo "WEBUI_API_CLIENT_PROTOCOL=$webapi_client_protocol"
echo "WEBUI_API_CLIENT_HOST=$webapi_client_host"
echo "WEBUI_API_CLIENT_PORT=$webapi_client_port"
echo ""
echo "WEBUI_API_PROXYPASS_PROTOCOL=$webapi_proxypass_protocol"
echo "WEBUI_API_PROXYPASS_HOST=$webapi_proxypass_host"
echo "WEBUI_API_PROXYPASS_PORT=$webapi_proxypass_port"
echo ""
echo "MSV3_API_PROXYPASS_PROTOCOL=$msv3_proxypass_protocol"
echo "MSV3_API_PROXYPASS_HOST=$msv3_proxypass_host"
echo "MSV3_API_PROXYPASS_PORT=$msv3_proxypass_port"
echo ""
echo "DEBUG_CAT_NGINX_CONF_FILE=$debug_cat_nginx_conf_file"
echo ""

if [ "$debug_cat_nginx_conf_file" != "n" ];
then
    echo "*************************************************************"
    echo "Output the nginx default.conf file:"
    echo "*************************************************************"
    cat $config_nginx_file
    echo ""
fi

echo "Start nginx -g 'daemon off;'"
nginx -g 'daemon off;'
