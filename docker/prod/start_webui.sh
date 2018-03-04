#!/bin/bash

# The variables have defaults that can be set from outside, e.g. via -e "DB_HOST=mydbms" or from the docker-compose.yml file.
# Also see https://docs.docker.com/engine/reference/builder/#environment-replacement
webapi_host=${WEBAPI_HOST:-webapi}
webapi_port=${WEBAPI_PORT:-8080}

local config_js_file=/opt/metasfresh-webui-frontend/dist/config.js
sed -Ei "s/WEBUI_API_HOST/${webapi_host}/g" $config_js_file
sed -Ei "s/WEBUI_API_PORT/${webapi_port}/g" $config_js_file

local ssl_config_file = /etc/apache2/sites-available/metasfresh_webui_ssl.conf
sed -Ei "s/WEBUI_API_HOST/${webapi_host}/g" $ssl_config_file
sed -Ei "s/WEBUI_API_PORT/${webapi_port}/g" $ssl_config_file

local non_ssl_config_file = /etc/apache2/sites-available/metasfresh_webui.conf
sed -Ei "s/WEBUI_API_HOST/${webapi_host}/g" $non_ssl_config_file
sed -Ei "s/WEBUI_API_PORT/${webapi_port}/g" $non_ssl_config_file

if [[ -f "/etc/apache2/certs/fullchain.pem" ]] && [[ -f "/etc/apache2/certs/privkey.pem" ]]; then
	sed -i 's/\bhttp\b/https/g' $config_js_file
	a2ensite metasfresh_webui_ssl.conf
	echo "[METASFRESH] Activated SSL!"
else 
	sed -i 's/\https\b/http/g' $config_js_file
	a2ensite metasfresh_webui.conf
	a2dissite metasfresh_webui_ssl.conf
	echo "[METASFRESH] Runnning Non-SSL!"
fi

/usr/sbin/apache2ctl -DFOREGROUND	
